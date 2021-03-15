package org.ivcode.stonks.indicator;

public enum IntervalField {
	OPEN {
		@Override
		public double getValue(Interval interval) {
			return interval.getOpen();
		}
	},
	CLOSE {
		@Override
		public double getValue(Interval interval) {
			return interval.getClose();
		}
	},
	HIGH {
		@Override
		public double getValue(Interval interval) {
			return interval.getHigh();
		}
	},
	LOW {
		@Override
		public double getValue(Interval interval) {
			return interval.getLow();
		}
	};
	
	public abstract double getValue(Interval interval);
}
