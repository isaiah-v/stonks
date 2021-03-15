package org.ivcode.stonks.utils.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ivcode.stonks.utils.math.Equation;
import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.Point;

public class IntegerIterable implements Iterable<Integer> {

	private final Equation eq;
	private final int count;

	public IntegerIterable(int first, int last, int count) {
		this.count = count;
		this.eq = LinearEquation.createLinearEquation(new Point(0, first), new Point(count-1, last));
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int x = 0;
			
			@Override
			public boolean hasNext() {
				return x<count;
			}

			@Override
			public Integer next() {
				if(x>=count) {
					throw new NoSuchElementException();
				}
				return (int) Math.round(eq.calculate(x++));
			}
		};
	}
}
