package org.ivcode.stonks.invester.ratings;

public interface RatingIndicatorVisitor <T,R> {
	R apply(VariableMovingAverageRating rating, T arrgument);
	R apply(WilliamsPercentRangeRating rating, T arrgument);
}
