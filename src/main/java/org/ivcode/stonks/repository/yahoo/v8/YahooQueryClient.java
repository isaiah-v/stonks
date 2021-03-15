package org.ivcode.stonks.repository.yahoo.v8;

import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooMarketTime;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YahooQueryClient {
	
	@GET("/v8/finance/chart/{symbol}?includePrePost=true")
	Call<ChartResponse> getChart(
			@Path("symbol") String symbol);
	
	@GET("/v8/finance/chart/{symbol}")
	Call<ChartResponse> getChart(
			@Path("symbol") String symbol,
			@Query("includePrePost") boolean includePrePost);
	
	@GET("/v8/finance/chart/{symbol}?includePrePost=true")
	Call<ChartResponse> getChart(
			@Path("symbol") String symbol,
			@Query("range") String range,
			@Query("interval") String interval);
	
	@GET("/v8/finance/chart/{symbol}")
	Call<ChartResponse> getChart(
			@Path("symbol") String symbol,
			@Query("includePrePost") boolean includePrePost,
			@Query("range") String range,
			@Query("interval") String interval);
	
	@GET("/v8/finance/chart/{symbol}?includePrePost=true")
	Call<ChartResponse> getChart(
			@Path("symbol") String symbol,
			@Query("period1") Long start,
			@Query("period2") Long stop,
			@Query("interval") String interval);
	
	@GET("/v8/finance/chart/{symbol}")
	Call<ChartResponse> getChart(
			@Path("symbol") String symbol,
			@Query("includePrePost") boolean includePrePost,
			@Query("period1") Long start,
			@Query("period2") Long stop,
			@Query("interval") String interval);
	
	@GET("/v6/finance/quote")
	Call<YahooQuote> getQuote(
			@Query("symbols") String symbols);
	
	@GET("/_finance_doubledown/api/resource/finance.market-time")
	Call<YahooMarketTime> getMarketTime();
}
