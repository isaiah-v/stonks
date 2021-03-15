package org.ivcode.stonks.utils.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ivcode.stonks.utils.math.Equation;
import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.Point;

public class LongIterable implements Iterable<Long> {

	private final Equation eq;
	private final int count;

	public LongIterable(long first, long last, int count) {
		this.count = count;
		this.eq = LinearEquation.createLinearEquation(new Point(0, first), new Point(count-1, last));
	}
	
	@Override
	public Iterator<Long> iterator() {
		return new Iterator<Long>() {
			int x = 0;
			
			@Override
			public boolean hasNext() {
				return x<count;
			}

			@Override
			public Long next() {
				if(x>=count) {
					throw new NoSuchElementException();
				}
				return (long) eq.calculate(x++);
			}
		};
	}
}
