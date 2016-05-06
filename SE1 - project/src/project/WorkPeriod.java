package project;

import Exceptions_Errors.WrongInputException;

//where should private getCurrentDay be placed?


public class WorkPeriod extends Object {
	public CalDay day;
	private double start;
	private double end;
	
	public WorkPeriod (CalDay day, double start, double end) throws WrongInputException {
		if (start<0) {
			throw new WrongInputException("Start time cannot be less than zero.");
		}
		if (end<0) {
			throw new WrongInputException("End time cannot be less than zero.");
		}
		if (start>24) {
			throw new WrongInputException("Start cannot be more than 24, due to the fact that there is only 24 hours each day.");
		}
		if(end>24){
			throw new WrongInputException("End cannot be more than 24, due to the fact that there is only 24 hours each day.");
		}
		this.day=day;
		this.start=start;
		this.end=end;
	}
	
	public double getLength () {
		return end-start;
	}
	
	public boolean equals(WorkPeriod other){
		if(this.day.equals(other.day) && this.start==other.start && this.end==other.end){
			return true;
		}
		return false;
	}
	
	public boolean overlapse(WorkPeriod other){
		if(!this.day.equals(other.day)){
			return false;
		}
		if(this.start>other.start && this.start<other.end){
			return true;
		}
		if(this.end>other.start && this.start<other.end){
			return true;
		}
		
		return false;
	}
	
	protected boolean isDay(CalDay day) {
		return this.day.equals(day);
	}
	
	protected boolean isWhile(CalWeek start, CalWeek end) {
		if (day.week.isAfterOrWhile(start) && day.week.isBeforeOrWhile(end)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
}

