package org.ivcode.stonks.market.simulator.properties;

public class SimulatorPropertiesBuilder {
	Integer daysToSettle;

	public SimulatorPropertiesBuilder withDaysToSettle(int days) {
		if (days < 0) {
			throw new IllegalArgumentException("must be positive");
		}

		this.daysToSettle = days;
		return this;
	}

	public SimulatorProperties build() {
		return new SimulatorProperties(this);
	}
}
