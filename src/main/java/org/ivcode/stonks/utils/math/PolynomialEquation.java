package org.ivcode.stonks.utils.math;

public class PolynomialEquation implements Equation {

	private double[] coefficients;
	
	
	public PolynomialEquation(double[] coefficients) {
		this.coefficients = new double[coefficients.length];
		System.arraycopy(coefficients, 0, this.coefficients, 0, this.coefficients.length);
	}
	
	@Override
	public double calculate(double x) {
		double value = 0;
		
		for(int i=0; i<coefficients.length; i++) {
			value += Math.pow(x, i) * coefficients[i];
		}
		
		return value;
	}
	
	/**
	 * Gets the n-th coefficient
	 * 
	 * n=0 -> The constant
	 * n=1 -> A linear equation's slope
	 * 
	 * @param n
	 * @return
	 */
	public double getCoefficient(int n) {
		return coefficients[n];
	}
	
	public int getCoefficientSize() {
		return coefficients.length;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("y =");
		
		int size = coefficients.length-1;
		for(int i=size; i>=0; i--) {
			if(i!=size) {
				sb.append(" +");
			}
			sb.append(' ').append(coefficients[i]);
			
			if(i>0) {
				sb.append('x');
			}
			
			if(i>1) {
				sb.append('^').append(i);
			}
		}
		
		return sb.toString();
	}
}
