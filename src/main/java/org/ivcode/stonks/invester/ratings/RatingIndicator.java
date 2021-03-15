package org.ivcode.stonks.invester.ratings;

import org.ivcode.stonks.invester.Initializable;
import org.ivcode.stonks.market.Tick;


public interface RatingIndicator extends Initializable {
	double getRating(Tick tick);
	public <T,R> R visit(RatingIndicatorVisitor<T,R> visitor, T argument);
}
