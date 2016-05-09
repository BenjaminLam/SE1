package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import Exceptions_Enums.WrongInputException;
/*
 * Authors: Benjamin, Christian, Asbjørn, Martin
 * 
 */


public class Assignment {
	public int taskID;
	public int employeeID;
	public List<WorkPeriod> bookings=new ArrayList<WorkPeriod>();
	public List<WorkPeriod> timeRegisters=new ArrayList<WorkPeriod>();
	
	public Assignment (Task task, Employee employee) {
		this.taskID=task.ID;
		this.employeeID=employee.ID;
		
	}
	
	public String toString () {
		return "Task id: " + taskID + " employee id " + employeeID;
	}
	
	public double getCumulativeTimeRegisters () {
		double cumulativeValue=0;
		
		for (WorkPeriod booking:timeRegisters) {
			cumulativeValue+=booking.getLength();
		}
		
		return cumulativeValue;
	}
	
	protected WorkPeriod addTimeRegister (WorkPeriod wp) throws WrongInputException{
		if (wp==null) {
			throw new WrongInputException("Booking doesn't exist");
		}
		timeRegisters.add(wp);
		return timeRegisters.get(timeRegisters.size()-1);
	}
	
	protected boolean addBooking (WorkPeriod wp){
		if (wp!=null) {
			bookings.add(wp);
			return true;
		}
		return false;
	}

	public List<WorkPeriod> getDayBookings(CalDay day) {
		List<WorkPeriod> todaysBookings=new ArrayList<WorkPeriod>();
		for (WorkPeriod wp:bookings) {
			if (wp.isDay(day)) todaysBookings.add(wp);
		}
		return todaysBookings;
	}
	
	protected WorkPeriod removeBooking(CalDay calDay, double start, double end) throws WrongInputException {
		WorkPeriod wp=getBooking(calDay,start,end);
		if(wp==null) throw new WrongInputException ("Booking doesn't excist");
		bookings.remove(wp);
		return wp; 
	}
	public WorkPeriod getBooking (CalDay calDay, double start, double end) {
		for (WorkPeriod wp:bookings) {
			if (wp.day.equals(calDay) && start == start && end ==end){
				return wp;
			}
		}
		return null;
	}
	
}
