package project;

import Exceptions_Errors.WrongInputException;

public class WorkPeriod extends Object {
	public CalDay day;
	private double start;
	private double end;
	
	public WorkPeriod (CalDay day, double start, double end) throws WrongInputException {
		if(start<0 || end<0){
			throw new WrongInputException("Input cannot be less than zero.");
		}
		if(start>24 || end>24){
			throw new WrongInputException("Input cannot be more than 24, due to the fact that there is only 24 hours each day.");
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
}
