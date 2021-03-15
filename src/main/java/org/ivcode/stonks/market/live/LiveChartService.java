package org.ivcode.stonks.market.live;

import static org.ivcode.stonks.market.live.YahooChartUtils.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.ivcode.stonks.market.ChartInterval;
import org.ivcode.stonks.market.ChartRange;
import org.ivcode.stonks.market.ChartService;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveChartService implements ChartService {

	@Autowired
	private YahooService yahooService;
	
	@Override
	public List<ChartTick> getChart(String symbol, ChartRange range, ChartInterval interval) throws IOException {
		ChartResponse response = yahooService.getChart(symbol, toRange(range), toInterval(interval));
		return toTicks(response);
	}

	@Override
	public List<ChartTick> getChart(String symbol, ChartRange range, ChartInterval interval, boolean includePerPost)
			throws IOException {
		
		ChartResponse response = yahooService.getChart(symbol, toRange(range), toInterval(interval), true);
		
		return includePerPost
				? toTicks(response)
				: toTicks(response).stream().filter(t->MarketStatus.OPEN.equals(t.getStatus())).collect(Collectors.toList());
	}

}
