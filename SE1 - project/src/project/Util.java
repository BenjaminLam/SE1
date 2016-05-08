package project;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Exceptions_Enums.WrongInputException;

public class Util {
	public static CalDay getCurrentDay () throws WrongInputException {
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
	
	public static CalWeek getCurrentWeek() throws WrongInputException {
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

	public static String[] splitString (String input) {
		return input.split(" ");
	}
	
	public static String[] stringListToArray (List<String> strings) {
		String[] array=new String[strings.size()];
		for (int i=0;i<strings.size();i++) {
			array[i]=strings.get(i);
		}
		return array;
	}
}
