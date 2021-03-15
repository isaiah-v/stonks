package org.ivcode.stonks.market;

import java.io.IOException;
import java.util.List;

public interface ChartService {
	List<ChartTick> getChart(String symbol, ChartRange range, ChartInterval interval) throws IOException;
	List<ChartTick> getChart(String symbol, ChartRange range, ChartInterval interval, boolean includePerPost) throws IOException;
}
