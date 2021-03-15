package org.ivcode.stonks.utils.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ivcode.stonks.utils.math.Equation;
import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.Point;

public class DoubleIterable implements Iterable<Double>  {

	private final Equation eq;
	private final int count;

	public DoubleIterable(double first, double last, int count) {
		this.count = count;
		this.eq = LinearEquation.createLinearEquation(new Point(0, first), new Point(count-1, last));
	}
	
	@Override
	public Iterator<Double> iterator() {
		return new Iterator<Double>() {
			int x = 0;
			
			@Override
			public boolean hasNext() {
				return x<count;
			}

			@Override
			public Double next() {
				if(x>=count) {
					throw new NoSuchElementException();
				}
				return eq.calculate(x++);
			}
		};
	}
}
