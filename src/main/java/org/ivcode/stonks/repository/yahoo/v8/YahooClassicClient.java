package org.ivcode.stonks.repository.yahoo.v8;

import org.ivcode.stonks.repository.yahoo.v8.model.YahooMarketTime;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YahooClassicClient {
	@GET("/_finance_doubledown/api/resource/finance.market-time")
	Call<YahooMarketTime> getMarketTime();
}
