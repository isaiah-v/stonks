package org.ivcode.stonks.utils.math;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class LeastSquaresRegression {
	
	private final List<Point> plot = new ArrayList<>();
	
	public LeastSquaresRegression with(double x, double y) {
		return with(new Point(x, y));
	}
	
	public synchronized LeastSquaresRegression with(Point point) {
		if(point==null) {
			throw new IllegalStateException("no point");
		}
		
		plot.add(point);
		return this;
	}
	
	/**
	 * Creates the best fitting equation with the given number of coefficients
	 * @param n the number of coefficients
	 * @return	The best fitting equation with the given number of coefficients
	 */
	public PolynomialEquation getEquation(int n) {
		// c = coefficient matrix
		// c = x^-1 * y
		
		
		RealMatrix x = new Array2DRowRealMatrix(matrixX(n));		
		RealMatrix y = new Array2DRowRealMatrix(matrixY(n));
		
		RealMatrix x_n1 = MatrixUtils.inverse(x);
		
		RealMatrix rm = x_n1.multiply(y);
		
		return new PolynomialEquation(rm.getColumn(0));
	}
	
	public double getCoefficientOfDetermination(Equation eq) {
		return 1 - (getResidualSumOfSquares(eq) / getTotalSumOfSquares());
	}
	
	private double getResidualSumOfSquares(Equation eq) {
		double rss = 0.0;
		
		for(Point p : plot) {
			rss += pow((p.getY()-eq.calculate(p.getX())) ,2);
		}
		
		return rss;
	}
	
	private double getTotalSumOfSquares() {
		double yAvg = getAverageY();
		
		double tss = 0;
		for(Point p : plot) {
			tss += pow((p.getY() - yAvg), 2);
		}
		
		return tss;
	}
	
	private double getAverageY() {
		double total = 0.0;
				
		for(Point p : plot) {
			total += p.getY();
		}
		
		return total / plot.size();
	}
	
	private double[][] matrixX(int n) {
		double [][] matrix = new double[n+1][];
		
		for(int i=0; i<matrix.length; i++) {
			double[] row = new double[matrix.length];
			matrix[i] = row;
			
			for(int j=0; j<row.length; j++) {
				int power = i+j;
				row[j] = sum(p->pow(p.getX(), power));
			}
		}
		
		return matrix;
	}
	
	private double[][] matrixY(int n) {
		double [][] matrix = new double[n+1][];
		
		for(int i=0; i<matrix.length; i++) {
			double[] row = new double[1];
			matrix[i] = row;
			
			int power = i;
			row[0] = sum(p-> pow(p.getX(),power) * p.getY());
		}
		
		return matrix;
	}
	
	private double sum(Function<Point, Double> func) {
		double total = 0;
		for(Point point : plot) {
			total += func.apply(point);
		}
		return total;
	}
}
