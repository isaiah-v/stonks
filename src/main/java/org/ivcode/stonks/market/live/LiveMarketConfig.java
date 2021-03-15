package org.ivcode.stonks.market.live;

import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiveMarketConfig {
	
	@Bean
	public MarketTime createMarketTime(YahooService yahooService) {
		LiveMarketTime marketTime = new LiveMarketTime(yahooService);
		
		Thread thread = new Thread(marketTime);
		thread.start();
		
		return marketTime;
	}
}
