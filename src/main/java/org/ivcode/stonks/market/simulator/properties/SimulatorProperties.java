package org.ivcode.stonks.market.simulator.properties;

import static org.apache.commons.lang3.Validate.*;

public class SimulatorProperties {

	private final int daysToSettle;

	SimulatorProperties(SimulatorPropertiesBuilder builder) {
		this.daysToSettle = notNull(builder.daysToSettle);
	}

	public int getDaysToSettle() {
		return daysToSettle;
	}
}
