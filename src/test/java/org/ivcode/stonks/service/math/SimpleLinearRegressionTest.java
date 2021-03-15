package org.ivcode.stonks.service.math;

import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.SimpleLinearRegression;
import org.junit.Test;

public class SimpleLinearRegressionTest {

	@Test
	public void test() {
		SimpleLinearRegression l = new SimpleLinearRegression()
				.with(43, 99)
				.with(21, 65)
				.with(25, 79)
				.with(42, 75)
				.with(57, 87)
				.with(59, 81);
		
		LinearEquation e = l.getEquation();
		System.out.println(e);
	}
}
