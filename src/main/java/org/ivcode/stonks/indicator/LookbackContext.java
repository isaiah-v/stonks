package org.ivcode.stonks.indicator;

import java.util.concurrent.TimeUnit;

public class LookbackContext {
	private final int lookback;
	private final int lookbackDuration;
	private final TimeUnit lookbackUnit;

	public LookbackContext(int lookback, int lookbackDuration, TimeUnit lookbackUnit) {
		this.lookback = lookback;
		this.lookbackDuration = lookbackDuration;
		this.lookbackUnit = lookbackUnit;
	}

	public int getLookback() {
		return lookback;
	}

	public int getLookbackDuration() {
		return lookbackDuration;
	}

	public TimeUnit getLookbackUnit() {
		return lookbackUnit;
	}
}
