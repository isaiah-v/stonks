package org.ivcode.stonks.invester.tradegov;

import java.util.List;

import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;

public class SimpleTradeGovernor implements TradeGovernor {

	@Override
	public void init(List<ChartTick> history) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getQuantity(double qualityRating, Tick tick) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T, R> R visit(TradeGovernorVisitor<T, R> visitor, T argument) {
		// TODO Auto-generated method stub
		return null;
	}

}
