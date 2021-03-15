package org.ivcode.stonks.utils.math;

public class LinearEquation implements Equation {
	
	private final double slope;
	private final double interceptY;
	
	public LinearEquation(double slope, double interceptY) {
		this.slope = slope;
		this.interceptY = interceptY;
	}

	public double getSlope() {
		return slope;
	}

	public double getInterceptY() {
		return interceptY;
	}
	
	@Override
	public double calculate(double x) {
		return slope * x + interceptY;
	}
	
	public double calculateX(double y) {
		return (y - this.interceptY) / this.slope;
	}

	@Override
	public String toString() {
		return "y = " + slope + "x + " + interceptY;
	}
	
	public static LinearEquation createLinearEquation(Point p1, Point p2) {
		double m = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
		double b = p1.getY() - (m * p1.getX());
		
		return new LinearEquation(m, b);
	}

}
