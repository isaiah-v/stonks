package org.ivcode.stonks.market.simulator.broker;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerSimulatorConfig {

	//@Bean("simulator.broker")
	public BrokerSimulator createBroker(BrokerSimulatorFactory factory) {
		return factory.createBrokerService();
	}
}
