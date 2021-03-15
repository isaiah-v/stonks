package org.ivcode.stonks.market.simulator.chart;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.ivcode.stonks.market.ChartInterval;
import org.ivcode.stonks.market.ChartRange;
import org.ivcode.stonks.market.ChartService;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.live.YahooChartUtils;
import org.ivcode.stonks.market.simulator.businessday.BusinessDays;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.ivcode.stonks.service.yahoo.v8.model.Interval;
import org.ivcode.stonks.utils.TimeUtils;

public class SimulatorChartService implements ChartService {

	private final YahooService yahooService;
	private final MarketTime marketTime;
	private final BusinessDays businessDays;
	
	private final ChartCache cache;

	public SimulatorChartService(YahooService yahooService, MarketTime marketTime, BusinessDays businessDays, ChartCache cache) {
		this.yahooService = yahooService;
		this.marketTime = marketTime;
		this.businessDays = businessDays;
		this.cache = cache;
	}

	@Override
	public synchronized List<ChartTick> getChart(String symbol, ChartRange range, ChartInterval interval) throws IOException {
		return getChart(symbol, range, interval, true);
	}

	@Override
	public synchronized List<ChartTick> getChart(String symbol, ChartRange range, ChartInterval interval, boolean includePerPost)
			throws IOException {

		LocalDate today = TimeUtils.toLocalDate(marketTime.getTime());
		LocalDate closestBusinessDate = businessDays.getBusinessDate(today, -1);
		LocalDate startDate = computeStartDate(closestBusinessDate, range);
		Interval yInterval = YahooChartUtils.toInterval(interval);

		ChartResponse response = cache.get(symbol, startDate, closestBusinessDate, yInterval);
		if(response==null) {
			response = yahooService.getChart(symbol, startDate, closestBusinessDate, yInterval);
			cache.put(symbol, startDate, closestBusinessDate, yInterval, response);
		}

		List<ChartTick> ticks = YahooChartUtils.toTicks(response).stream().filter(t -> isWithinRange(t, startDate, closestBusinessDate)).collect(toList());
		return includePerPost ? ticks : ticks.stream().filter(SimulatorChartService::isOpenTick).collect(toList());
	}
	
	private static boolean isWithinRange(ChartTick tick, LocalDate startDate, LocalDate endDate) {
		LocalDate date = TimeUtils.toLocalDate(tick.getDate());
		
		return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
	}
	
	private static boolean isOpenTick(ChartTick tick) {
		return MarketStatus.OPEN.equals(tick.getStatus());
	}

	private static LocalDate computeStartDate(LocalDate date, ChartRange range) {
		switch (range) {
		case ONE_DAY:
			return date.minusDays(1);
		case FIVE_DAY:
			return date.minusDays(5);
		case ONE_MONTH:
			return date.minusMonths(1);
		case SIX_MONTH:
			return date.minusMonths(6);
		case ONE_YEAR:
			return date.minusYears(1);
		case FIVE_YEAR:
			return date.minusYears(5);
		case YTD:
			return date.with(TemporalAdjusters.firstDayOfYear());
		case MAX:
			return date.minusYears(5);
		default:
			throw new IllegalArgumentException("unknown range: " + range);
		}
	}
}
