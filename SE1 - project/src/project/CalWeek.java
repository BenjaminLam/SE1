package project;

import Exceptions_Enums.WrongInputException;

public class CalWeek {
	public int year;
	public int week;
	
	public CalWeek(int year, int week) throws WrongInputException{
		this.year=year;
		this.week=week;
		
		if (week<1 || week>53) throw new WrongInputException ("Week has to be a number between 1 and 53");
		if (year<1 || year>2050) throw new WrongInputException("Year has to be a number between 1 and 2050");
	}
	
	public boolean isAfterOrWhile (CalWeek week) {
		return (isAfter(week)||isWhile(week));
	}
	
	public boolean isAfter (CalWeek week) {
		if (week.year<this.year) return true;
		if (week.year>this.year) return false;
		return (week.week<this.week);
	}
	
	public boolean isWhile (CalWeek week) {
		return (week.year==this.year && week.week==this.week); 
	}
	
	public boolean isBeforeOrWhile (CalWeek week) {
		return (isBefore(week)||isWhile(week));
	}
	
	public boolean isBefore(CalWeek week) {
		if (week.year>this.year) return true;
		if (week.year<this.year) return false;
		return (week.week>this.week);
	}
	
	public boolean equals(CalWeek other){
		return (this.year==other.year && this.week==other.week);
	}
	
	public String toString() {
		return("Year: " + year + " Week " + week);
	}
	
	public int weeksBetween(CalWeek other){
		int weeksBetween=0;
		if (other.year!=this.year) {
			weeksBetween+=Util.abs(other.year-this.year)*52;
			
			CalWeek max;
			CalWeek min;
			if (other.year>this.year) {
				max=other;
				min=this;
			}
			else {
				max=this;
				min=other;
			}
			
			weeksBetween+=max.week;
			weeksBetween+=52-min.week;
		}
		
		weeksBetween+=Util.abs(other.week-this.week);
	
		return weeksBetween;
	}
}
