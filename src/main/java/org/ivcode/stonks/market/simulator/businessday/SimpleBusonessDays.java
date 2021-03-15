package org.ivcode.stonks.market.simulator.businessday;

import static org.ivcode.stonks.utils.TimeUtils.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleBusonessDays implements BusinessDays {

	private static final MonthDay NEW_YEARS_DAY = MonthDay.parse("--01-01");
	private static final MonthDay INDEPENDENCE_DAY = MonthDay.parse("--07-04");
	private static final MonthDay CHRISTMAS_DAY = MonthDay.parse("--12-25");
	
	@Override
	public int getBusinessDays(Date from, Date to) {
		return getBusinessDays(toLocalDate(from), toLocalDate(to));
	}

	@Override
	public int getBusinessDays(LocalDate from, LocalDate to) {
		Set<LocalDate> hollidays = getHolidays(from.getYear(), to.getYear());
		
		int days = 0;
		
		LocalDate today=from;
		while(today.compareTo(to)<=0) {
			if(isBusinessDay(today, hollidays)) {
				days++;
			}
			
			today = today.plusDays(1);
		}
		
		return days;
	}
	
	@Override
	public LocalDate getBusinessDate(LocalDate date, int businessDays) {
		int adder = businessDays<0 ? -1 : 1;
		int days = businessDays<0 ? -1 * (businessDays + 1) : businessDays;
		
		int startYear = businessDays<0 ? date.minusDays(days*2).getYear() : date.getYear();
		int endYear = businessDays<0 ? date.getYear() : date.plusDays(days*2).getYear();
		
		Set<LocalDate> hollidays = getHolidays(startYear, endYear);
		
		LocalDate businessDate = date;
		for(int i=businessDays; i>=0;) {
			if(isBusinessDay(businessDate, hollidays)) {
				i--;
			}
			businessDate = businessDate.plusDays(adder);
		}
		
		return businessDate;
	}

	private boolean isBusinessDay(LocalDate date, Set<LocalDate> hollidays) {
		return !isWeekend(date) && !hollidays.contains(date);
	}
	
	private static boolean isWeekend(LocalDate date) {
		return isWeekend(date.getDayOfWeek());
	}
	
	private static boolean isWeekend(DayOfWeek day) {
		return DayOfWeek.SATURDAY.equals(day) || DayOfWeek.SUNDAY.equals(day);
	}
	
	private static Set<LocalDate> getHolidays(int yearFrom, int yearTo) {
		Set<LocalDate> dates = new HashSet<>();
		
		for(int year=yearFrom; year<=yearTo; year++) {
			dates.addAll(getHolidays(year));
		}
		
		return dates;
	}
	
	private static List<LocalDate> getHolidays(int year) {
		List<LocalDate> dates = new ArrayList<>(9);
		
		LocalDate d;
		
		d = getNewYearDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getMartinLutherKingJrDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getPresidentsDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getGoodFriday(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getMemorialDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getIndependenceDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getLaborDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getThanksgivingDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		d = getChristmasDay(year);
		if(d!=null) {
			dates.add(d);
		}
		
		return dates;
	}
	
	private static LocalDate getNewYearDay(int year) {
		LocalDate newYearsDate = NEW_YEARS_DAY.atYear(year);
		DayOfWeek dayOfWeek = newYearsDate.getDayOfWeek();
		
		if(!isWeekend(dayOfWeek)) {
			// if new years day dosen't fall on the weekend, we observe it on the day
			return newYearsDate;
		} else if(DayOfWeek.SATURDAY.equals(dayOfWeek)) {
			// if new years falls on a Saturday, we don't observe it
			return null;
		} else {
			// if new years falls on a Sunday, we observe it on the next monday
			return newYearsDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		}
		
	}
	
	private static LocalDate getIndependenceDay(int year) {
		LocalDate independenceDay = INDEPENDENCE_DAY.atYear(year);
		DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
		
		if(!isWeekend(dayOfWeek)) {
			// if it's not the weekend, we observe it on the day
			return independenceDay;
		} else if(DayOfWeek.SATURDAY.equals(dayOfWeek)) {
			// if it's on Saturday, we observe it on Friday
			return independenceDay.minusDays(1);
		} else {
			// if it's on Sunday, we observe it on Monday
			return independenceDay.plusDays(1);
		}
		
	}
	
	private static LocalDate getMartinLutherKingJrDay(int year) {
		//  Third Monday in January
		return LocalDate
				.of(year, Month.JANUARY, 1)
				.with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY));
	}
	
	private static LocalDate getPresidentsDay(int year) {
		// Third Monday in February
		
		return LocalDate
				.of(year, Month.FEBRUARY, 1)
				.with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY));
	}
	
	private static LocalDate getMemorialDay(int year) {
		// Last Monday in may
		
		return LocalDate
				.of(year, Month.MAY, 1)
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
	}
	
	private static LocalDate getLaborDay(int year) {
		// First Monday in September
		
		return LocalDate
				.of(year, Month.SEPTEMBER, 1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
	}
	
	private static LocalDate getThanksgivingDay(int year) {
		// 4th Thursday in November 
		
		return LocalDate
				.of(year, Month.NOVEMBER, 1)
				.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
	}
	
	private static LocalDate getChristmasDay(int year) {
		LocalDate christmasDay = CHRISTMAS_DAY.atYear(year);
		DayOfWeek dayOfWeek = christmasDay.getDayOfWeek();
		
		if(!isWeekend(dayOfWeek)) {
			// if Christmas isn't in the weekend, it's observed on Christmas day
			return christmasDay;
		} else if(DayOfWeek.SATURDAY.equals(dayOfWeek)) {
			// if Christmas is on Saturday, it's observed on the Friday before Christmas
			return christmasDay.minusDays(1);
		} else {
			// if Christmas is on Sunday, it's observed on the following Monday
			return christmasDay.plusDays(1);
		}
	}
	
	private static LocalDate getGoodFriday(int year) {
		// The Friday before Easter Sunday
		
		LocalDate localDate = getEasterSunday(year);
		return localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
	}
	
	private static LocalDate getEasterSunday(int year) {
		// Oskar Wieland's algorithm 
		
	    int day = 0;
	    int month = 0;

	    
	    int g = year % 19;
	    int c = year / 100;
	    int h = (c - (int)(c / 4) - (int)((8 * c + 13) / 25) + 19 * g + 15) % 30;
	    int i = h - (int)(h / 28) * (1 - (int)(h / 28) * (int)(29 / (h + 1)) * (int)((21 - g) / 11));

	    day   = i - ((year + (int)(year / 4) + i + 2 - c + (int)(c / 4)) % 7) + 28;
	    month = 3;

	    if (day > 31) {
	        month++;
	        day -= 31;
	    }

	    return LocalDate.of(year, month, day);
	}
}
