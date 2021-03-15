package org.ivcode.stonks.invester.ratings.iterable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.invester.ratings.LinearRegressionSlopeRating;
import org.ivcode.stonks.invester.ratings.VariableMovingAverageRating;
import org.ivcode.stonks.invester.ratings.WilliamsPercentRangeRating;
import org.ivcode.stonks.utils.Factory;
import org.ivcode.stonks.utils.iterators.DoubleIterable;
import org.ivcode.stonks.utils.iterators.IntegerIterable;
import org.ivcode.stonks.utils.iterators.TypedCombinationIterable;

public class RatingsIterables {
	
	public static Iterable<Factory<WilliamsPercentRangeRating>> williamsRating() {
		return williamsRating (
				new DoubleIterable(0.0, 0.6, 20),
				new DoubleIterable(0.4, 1.0, 20),
				new IntegerIterable(8, 40, 20));
	}
	
	public static Iterable<Factory<WilliamsPercentRangeRating>> williamsRating(
			Iterable<Double> overbought,
			Iterable<Double> oversold,
			Iterable<Integer> count) {
		
		Function<List<Object>, Factory<WilliamsPercentRangeRating>> func = l -> {
			Double ob = (Double)l.get(0);
			Double os = (Double)l.get(1);
			Integer lookback = (Integer)l.get(2);
			
			return WilliamsPercentRangeRating.createRateableFactory(ob, os, new LookbackContext(lookback, 1, TimeUnit.HOURS));
		};
		
		return new TypedCombinationIterable<>(func, overbought, oversold, count);
	}
	
	public static Iterable<Factory<LinearRegressionSlopeRating>> lrSlope() {
		return lrSlope (
				new DoubleIterable(0, 0.5, 10),
				new DoubleIterable(-0.5, 0, 10),
				new IntegerIterable(11, 12, 2));
	}
	
	public static Iterable<Factory<LinearRegressionSlopeRating>> lrSlope(
			Iterable<Double> low,
			Iterable<Double> high,
			Iterable<Integer> count){
		
		Function<List<Object>, Factory<LinearRegressionSlopeRating>> func = l -> {
			Double lo = (Double)l.get(0);
			Double hi = (Double)l.get(1);
			Integer lookback = (Integer)l.get(2);
			
			return LinearRegressionSlopeRating.createRateableFactory(lo, hi, new LookbackContext(lookback, 1, TimeUnit.HOURS));
		};
		
		return new TypedCombinationIterable<>(func, low, high, count);
	}
	
	public static Iterable<Factory<VariableMovingAverageRating>> vmr() {
		return vmr(
				new DoubleIterable(0.01, 0.1, 5),
				new IntegerIterable(8, 40, 5));
	}
	
	public static Iterable<Factory<VariableMovingAverageRating>> vmr(
			Iterable<Double> bound,
			Iterable<Integer> count) {
		
		Function<List<Object>, Factory<VariableMovingAverageRating>> func = l -> {
			Double b = (Double)l.get(0);
			Integer lookback = (Integer)l.get(1);
			
			
			return VariableMovingAverageRating.createRateableFactory(b, new LookbackContext(lookback, 1, TimeUnit.HOURS));
		};
		
		return new TypedCombinationIterable<>(func, bound, count);
	}
}
