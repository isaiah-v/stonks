package org.ivcode.stonks.invester;

import java.util.List;

import org.ivcode.stonks.market.ChartTick;

public interface Initializable {
	void init(List<ChartTick> history);;
}
