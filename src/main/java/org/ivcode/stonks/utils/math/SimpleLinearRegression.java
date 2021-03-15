package org.ivcode.stonks.utils.math;

import java.util.ArrayList;
import java.util.List;

public class SimpleLinearRegression {
	
	private final List<Point> plot = new ArrayList<>();
	
	public SimpleLinearRegression with(double x, double y) {
		return with(new Point(x, y));
	}
	
	public synchronized SimpleLinearRegression with(Point point) {
		if(point==null) {
			throw new IllegalStateException("no point");
		}
		
		plot.add(point);
		return this;
	}
	
	public synchronized LinearEquation getEquation() {
		double sumX = 0.0;
		double sumY = 0.0;
		
		for(Point p : plot) {
			sumX += p.getX();
			sumY += p.getY();
		}
		
		double sumXY = 0.0;
		double sumXSquared = 0.0;
		double sumYSquared = 0.0;
				
		for(Point p : plot) {
			double xy = p.getX() * p.getY();
			double xSquared = p.getX() * p.getX();
			double ySquared = p.getY() * p.getY();
			
			sumXY += xy;
			sumXSquared += xSquared;
			sumYSquared += ySquared;
		}
		
		int size = plot.size();
		
		double numerator = (size*sumXSquared)-(sumX*sumX);
		
		double a = ((sumY*sumXSquared)-(sumX*sumXY)) / numerator;
		double b = ((size*sumXY)-(sumX*sumY)) / numerator;
		
		LinearEquation equation = new LinearEquation(b, a);
		return equation;
	}
}
