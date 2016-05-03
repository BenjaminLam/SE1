package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Util {
	public static CalDay getCurrentDay () {
		Calendar calendar=new GregorianCalendar();
		int year=calendar.get(Calendar.YEAR);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		int day=calendar.get(Calendar.DAY_OF_WEEK);
		
		return new CalDay(new CalWeek(year,week),day);
	}
	
	public static int getCurrentYear() {
		Calendar calendar=new GregorianCalendar();
		return calendar.get(Calendar.YEAR);
	}
	
	public static CalWeek getCurrentWeek() {
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);

		return new CalWeek(year, week);
	}
	
	public static int abs (int tal) {
		if (tal<0) return -tal;
		return tal;
	}

	public static int max (int tal1, int tal2) {
		if (tal1>tal2) return tal1;
		return tal2;
	}
	
	public static int min (int tal1, int tal2) {
		if (tal1<tal2) return tal1;
		return tal2;
	}
}