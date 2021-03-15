package org.ivcode.stonks.utils.math;

public class PolynomialEquationHelper {
	
	public static LinearEquation toLinearEquation(PolynomialEquation eq) {
		if(eq.getCoefficientSize()!=2) {
			throw new IllegalArgumentException("the given equation is not linear");
		}
		
		return new LinearEquation(eq.getCoefficient(1), eq.getCoefficient(0));
	}
}
