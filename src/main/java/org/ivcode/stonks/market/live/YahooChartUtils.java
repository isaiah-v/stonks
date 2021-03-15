package org.ivcode.stonks.market.live;

import static java.util.Collections.emptyList;
import static org.ivcode.stonks.utils.EpochUtils.toDate;
import static org.ivcode.stonks.utils.SaftyUtils.safe;

import java.util.ArrayList;
import java.util.List;

import org.ivcode.stonks.market.ChartInterval;
import org.ivcode.stonks.market.ChartRange;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.repository.yahoo.v8.model.Chart;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartIndicators;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartMeta;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartQuote;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResult;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartTradingPeriod;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartTradingPeriods;
import org.ivcode.stonks.service.yahoo.v8.model.Interval;
import org.ivcode.stonks.service.yahoo.v8.model.Range;

public class YahooChartUtils {
	
	
	public static ChartResult getChartResult(ChartResponse response) {
		if(response==null) {
			return null;
		}
		
		Chart chart = response.getChart();
		if(chart==null) {
			return null;
		}
		
		List<ChartResult> results = chart.getResult();
		if(results.size()!=1) {
			throw new IllegalStateException("one result assumption is incorrect");
		}
		return results.get(0);
	}
	
	public static ChartMeta getChartMeta(ChartResponse response) {
		ChartResult result = getChartResult(response);
		return result==null ? null : result.getMeta();
	}
	
	public static ChartTradingPeriods getChartTradingPeriods(ChartResponse response) {
		ChartMeta meta = getChartMeta(response);
		return meta==null ? null : meta.getTradingPeriods();
	}
	
	public static List<ChartTick> toTicks(ChartResponse response) {
		ChartMeta meta = getChartMeta(response);
		if(meta==null) {
			return emptyList();
		}
		
		String symbol = meta.getSymbol();
		
		ChartResult result = getChartResult(response);
		return toChartTick(symbol, result, getChartTradingPeriods(response));
		
	}
	
	private static List<ChartTick> toChartTick(String symbol, ChartResult result, ChartTradingPeriods chartTradingPeriods) {
		if(result==null) {
			return emptyList();
		}
		
		List<Long> timestamps = result.getTimestamp();
		if(timestamps==null) {
			return emptyList();
		}
		
		ChartIndicators indicators = result.getIndicators();
		if(indicators==null) {
			return emptyList();
		}
		
		ChartQuote quote = safe(indicators.getQuote()).stream().findFirst().orElse(null);
		if(quote==null) {
			return emptyList();
		}
		
		List<Double> opens = quote.getOpen();
		List<Double> closes = quote.getClose();
		List<Double> lows = quote.getLow();
		List<Double> highs = quote.getHigh();
		List<Long> volumes = quote.getVolume();
		
		List<ChartTick> ticks = new ArrayList<>();
		
		int size = timestamps.size();
		for(int i=0; i<size; i++) {
			Long time = timestamps.get(i);
			Double open = opens.get(i);
			Double close = closes.get(i);
			Double low = lows.get(i);
			Double high = highs.get(i);
			Long volume = volumes.get(i);
			
			if(open==null && close==null && low==null && high==null) {
				continue;
			}
			
			ChartTick tick = new ChartTick();
			tick.setSymbol(symbol);
			tick.setDate(toDate(time));
			tick.setOpen(open);
			tick.setClose(close);
			tick.setLow(low);
			tick.setHigh(high);
			tick.setVolume(volume);
			tick.setStatus(getMarketStatus(time, chartTradingPeriods));
			
			ticks.add(tick);
		}
		
		return ticks;
	}
	
	private static MarketStatus getMarketStatus(long time, ChartTradingPeriods chartTradingPeriods) {
		if(isInWindow(time, chartTradingPeriods.getPre())) {
			return MarketStatus.PRE_MARKET;
		}
		
		if(isInWindow(time, chartTradingPeriods.getRegular())) {
			return MarketStatus.OPEN;
		}
		
		if(isInWindow(time, chartTradingPeriods.getPost())) {
			return MarketStatus.POST_MARKET;
		}
		
		return MarketStatus.CLOSED;
	}
	
	private static boolean isInWindow(long time, List<List<ChartTradingPeriod>> periods) {
		for(List<ChartTradingPeriod> periodList : periods) {
			for(ChartTradingPeriod period : periodList) {
				if(time>=period.getStart() && time<=period.getEnd()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static Interval toInterval(ChartInterval interval) {
		switch (interval) {
		case FIFTEEN_MINUTE:
			return Interval.FIFTEEN_MINUTE;
		case FIVE_MINUTE:
			return Interval.FIVE_MINUTE;
		case NINETY_MINUTE:
			return Interval.NINETY_MINUTE;
		case ONE_DAY:
			return Interval.ONE_DAY;
		case ONE_HOUR:
			return Interval.ONE_HOUR;
		case ONE_MINUTE:
			return Interval.ONE_MINUTE;
		case ONE_MONTH:
			return Interval.ONE_MONTH;
		case ONE_WEEK:
			return Interval.ONE_WEEK;
		case SIXTY_MINUTE:
			return Interval.SIXTY_MINUTE;
		case THIRTY_MINUTE:
			return Interval.THIRTY_MINUTE;
		case THREE_MONTH:
			return Interval.THREE_MONTH;
		case TWO_MINUTE:
			return Interval.TWO_MINUTE;
		default:
			throw new IllegalArgumentException("unknown interval");
		}
	}
	
	public static Range toRange(ChartRange range) {
		switch(range) {
		case ONE_DAY:
			return Range.ONE_DAY;
		case FIVE_DAY:
			return Range.FIVE_DAY;
		case ONE_MONTH:
			return Range.ONE_MONTH;
		case SIX_MONTH:
			return Range.SIX_MONTH;
		case ONE_YEAR:
			return Range.ONE_YEAR;
		case FIVE_YEAR:
			return Range.FIVE_YEAR;
		case YTD:
			return Range.YEAR_TO_DATE;
		case MAX:
			return Range.MAX;
		default:
			throw new IllegalArgumentException("unknown chart range: " + range);
		}
	}
}
