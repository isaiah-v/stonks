package org.ivcode.stonks.market.simulator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulatorPropertiesFactory {
	
	@Value("${simulator.daysToSettle}")
	private int daysToSettle;
	
	@Bean
	public SimulatorProperties createSimulatorProperties() {
		return new SimulatorPropertiesBuilder()
				.withDaysToSettle(daysToSettle)
				.build();
	}
}
