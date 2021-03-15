package org.ivcode.stonks.indicator;

import static org.ivcode.stonks.utils.SaftyUtils.safe;

public class IntervalManagerHelper {
	
	public static Interval getSummary(IntervalManager manager) {
		MutableInterval temp = new MutableInterval();
		
		boolean isFirst = true;
		for(Interval i : manager.getIntervals(false)) {
			if(isFirst) {
				isFirst = false;
				temp.setOpen(i.getOpen());
				temp.setStart(i.getStart());
			}
			
			update(temp, i.getHigh(), i.getLow(), i.getVolume());
		}
		
		Interval workingInterval = manager.getWorkingInterval();
		if(isFirst) {
			temp.setOpen(workingInterval.getOpen());
			temp.setStart(workingInterval.getStart());
		}
		
		update(temp, workingInterval.getHigh(), workingInterval.getLow(), workingInterval.getVolume());
		temp.setClose(workingInterval.getClose());
		temp.setEnd(workingInterval.getEnd());
		
		return new Interval(temp);
	}
	
	private static void update(MutableInterval context, double high, double low, long volume) {
		Double oldLow = context.getLow();
		if(oldLow==null || low<oldLow) {
			context.setLow(low);
		}
		
		Double oldHigh = context.getHigh();
		if(oldHigh==null || high>oldHigh) {
			context.setHigh(high);
		}
		
		Long oldVolume = context.getVolume();
		context.setVolume(safe(oldVolume) + volume);
	}
}
