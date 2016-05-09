package project;
/*
 * Authors: Benjamin, Christian, Asbjørn, Martin
 * 
 */

import java.util.ArrayList;
import java.util.List;

import Exceptions_Enums.WrongInputException;

public class CalDay {
	public CalWeek week;
	public int day;
	
	public CalDay (CalWeek week, int day) throws WrongInputException {
		this.week=week;
		this.day=day;
		
		if (day<1 || day>7) throw new WrongInputException ("Weekday has to be a number between 1 and 7");
	}
	
	public boolean equals(CalDay other){
		if((this.day==other.day)&&(this.week.equals(other.week))){
			return true;
		}
		return false;
	}
	
	public List<CalDay> getDaysBetween(CalDay other) throws WrongInputException{
		List<CalDay> daysBetween = new ArrayList<CalDay>();
		int thisWeek = this.week.week;
		int otherWeek = other.week.week;
		
		for(int i=thisWeek;i<=otherWeek; i++){
			for(int j=1; j<=5;j++){
				if(!(i==thisWeek && j<this.day)){
					if(!(i==otherWeek && j>other.day)){
						daysBetween.add(new CalDay(new CalWeek(this.week.year,i),j));
					}
				}
			}
		}
		return daysBetween; 
	}
}
