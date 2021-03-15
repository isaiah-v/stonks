package org.ivcode.stonks.invester.tradegov;

import org.ivcode.stonks.invester.Initializable;
import org.ivcode.stonks.market.Tick;

public interface TradeGovernor extends Initializable {
	int getQuantity(double qualityRating, Tick tick);
	<T, R> R visit(TradeGovernorVisitor<T,R> visitor, T argument);
}
