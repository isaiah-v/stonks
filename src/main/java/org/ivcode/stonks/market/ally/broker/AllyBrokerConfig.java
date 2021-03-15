package org.ivcode.stonks.market.ally.broker;

import java.io.IOException;

import org.ivcode.stonks.properties.StonkPropertiesService;
import org.ivcode.stonks.repository.ally.AllyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllyBrokerConfig {

	@Bean
	public AllyBroker createAllyBroker(
			AllyClient client,
			@Value("${ally.account}") String accountId,
			StonkPropertiesService properties) throws IOException {
		
		Double tradeMax = properties.getTradeMax();
		return new AllyBroker(client, accountId, properties, tradeMax);
	}
}
