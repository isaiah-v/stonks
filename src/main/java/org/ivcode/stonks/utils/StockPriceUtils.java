package org.ivcode.stonks.utils;

public class StockPriceUtils {
		public static Double getValueFromPeRatio(double peRatio, double earningsPerShare) {
			return peRatio * earningsPerShare;
		}
}
