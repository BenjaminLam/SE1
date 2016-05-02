package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Errors.*;
import project.*;

/**
 * Test for user case RegisterDailyTime
 * @author BenjaminWrist
 * Only one alternative scenario. For further info read rapport. 
 */

public class RegisterDailyTimeTest extends SampleDataSetup0{
	/*
	 * Main scenario: 
	 * 
	 * The systems presents an employees scheduled bookings. 
	 * The user chooses which bookings he/she has worked on.
	 * A timeRegister is created for every selected booking.
	 */
	
	@Test
	public void registerDailyTimeMain(){
		Employee employee = database.employees.get(9);
		CalDay day = new CalDay(new CalWeek(2000,2),1);
		int lastAss=database.assignments.size()-1;
		assertNotNull(employee);
		assertNotNull(day);
		
		List<WorkPeriod> todaysBookings = (List<WorkPeriod>) database.employeesTodaysBookings(employee, day).mainInfo;
		for(WorkPeriod booking: todaysBookings){
			database.copyBookingToTimeRegister(booking,database.assignments.get(lastAss));	
		}
		
		assertEquals(todaysBookings.size(),database.assignments.get(lastAss).timeRegisters.size());
	}
	
	
	/*
	 * The user tries manually register a task he/she is not working on
	 */
	
	@Test
	public void registerDailyTimeAlt1() throws WrongInputException{
		Employee employee = database.employees.get(database.assignments.size()-1);
		CalDay day = new CalDay(new CalWeek(2000,2),1);
		database.currentEmp=employee;
		
		assertNotNull(employee);
		assertNotNull(day);
		
		//This should be false since employee 9 not works on task 0. (see sampledatasetup)
		assertFalse(database.registerWorkManually(0, 9, 11, day));
		
		
		
		
	}
	
}
