package org.ivcode.stonks.market.simulator.event;

import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.market.live.YahooChartUtils;
import org.ivcode.stonks.market.simulator.event.TickTransformer.TickPrice;
import org.ivcode.stonks.repository.yahoo.v8.model.Chart;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartTradingPeriod;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartTradingPeriods;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.ivcode.stonks.service.yahoo.v8.model.Interval;
import org.ivcode.stonks.utils.EpochUtils;
import org.ivcode.stonks.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventFactory {

	private static final int WINDOW_DURATION_DAYS = 14;
	private static final Interval INTERVAL = Interval.FIVE_MINUTE;

	private final YahooService yahooService;

	@Autowired
	public EventFactory(YahooService yahooService) {
		this.yahooService = yahooService;
	}

	public List<Event> getEvents(Collection<String> symbols, LocalDate start, LocalDate end) throws IOException {
		List<Event> events = new ArrayList<>();

		LocalDate windowStartDate = start;
		LocalDate windowEndDate = windowStartDate.plusDays(WINDOW_DURATION_DAYS);

		while (windowStartDate.isBefore(end) || windowStartDate.isEqual(end)) {
			events.addAll(next(symbols, windowStartDate, windowEndDate));

			windowStartDate = windowEndDate.plusDays(1);
			windowEndDate = windowStartDate.plusDays(WINDOW_DURATION_DAYS);
		}

		filter(events, start, end);
		Collections.sort(events);
		return events;
	}
	
	private void filter(List<Event> events, LocalDate start, LocalDate end) {
		Iterator<Event> it = events.iterator();
		while(it.hasNext()) {
			Event e = it.next();
			LocalDate date = e.getTime().toLocalDate();
			if(date.isBefore(start) || date.isAfter(end)) {
				it.remove();
			}
		}
	}

	private Collection<Event> next(Collection<String> symbols, LocalDate start, LocalDate end) throws IOException {
		List<Event> events = new ArrayList<Event>();

		boolean isFirst = true;
		for (String symbol : symbols) {
			ChartResponse response = yahooService.getChart(symbol, start, end, INTERVAL);
			
			if(isFirst) {
				events.addAll(parseTimeEvents(start, response));
				isFirst=false;
			}
			
			events.addAll(parseTickEvents(response));
		}
		
		return events.stream().filter(e->isInRange(e, start, end)).collect(Collectors.toList());
	}
	
	private static boolean isInRange(Event event, LocalDate start, LocalDate end) {
		LocalDate eventDate = event.getTime().toLocalDate();
		return (eventDate.isEqual(start) || eventDate.isAfter(start)) && (eventDate.isEqual(end) || eventDate.isBefore(end));
	}

	private Collection<Event> parseTickEvents(ChartResponse response) {
		Chart chart = response.getChart();
		if (chart == null) {
			return Collections.emptyList();
		}

		List<ChartTick> chartTicks = YahooChartUtils.toTicks(response);
		List<Tick> ticks = TickTransformer.toTick(chartTicks, TickPrice.CLOSE);

		return ticks.stream().map(EventFactory::toTickEvent).collect(Collectors.toList());
	}

	private Collection<Event> parseTimeEvents(LocalDate start, ChartResponse response) {
		List<Event> events = new ArrayList<>();
		events.add(new TimeEvent(start.atStartOfDay(), MarketStatus.CLOSED));

		ChartTradingPeriods chartTradingPeriods = YahooChartUtils.getChartTradingPeriods(response);
		events.addAll(
				toTimeEvents(chartTradingPeriods.getPre(), MarketStatus.PRE_MARKET, ChartTradingPeriod::getStart));
		events.addAll(toTimeEvents(chartTradingPeriods.getRegular(), MarketStatus.OPEN, ChartTradingPeriod::getStart));
		events.addAll(
				toTimeEvents(chartTradingPeriods.getPost(), MarketStatus.POST_MARKET, ChartTradingPeriod::getStart));
		events.addAll(toTimeEvents(chartTradingPeriods.getPost(), MarketStatus.CLOSED, ChartTradingPeriod::getEnd));

		return events;
	}

	private static Event toTickEvent(Tick tick) {
		return new TickEvent(tick.getTime(), tick);
	}

	private static Collection<Event> toTimeEvents(List<List<ChartTradingPeriod>> periods, MarketStatus marketStatus,
			Function<ChartTradingPeriod, Long> getter) {
		List<Event> events = new ArrayList<>();

		for (List<ChartTradingPeriod> periodList : safe(periods)) {
			for (ChartTradingPeriod period : periodList) {
				Date date = EpochUtils.toDate(getter.apply(period));
				events.add(new TimeEvent(TimeUtils.toLocalDateTime(date), marketStatus));
			}
		}

		return events;
	}
}
