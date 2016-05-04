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

public class RegisterDailyTimeTest extends SampleDataSetupTest{
	/*
	 * Main scenario: 
	 * 
	 * The systems presents an employees scheduled bookings. 
	 * The user chooses which bookings he/she has worked on.
	 * A timeRegister is created for every selected booking.
	 */
	
	@Test
	public void registerDailyTimeMain(){
		Employee employee = sysApp.employees.get(9);
		CalDay day = new CalDay(new CalWeek(2000,2),1);
		int lastAss=sysApp.assignments.size()-1;
		assertNotNull(employee);
		assertNotNull(day);
		
		List<WorkPeriod> todaysBookings = (List<WorkPeriod>) sysApp.employeesTodaysBookings(employee, day).mainInfo;
		for(WorkPeriod booking: todaysBookings){
			sysApp.copyBookingToTimeRegister(booking,sysApp.assignments.get(lastAss));	
		}
		
		assertEquals(todaysBookings.size(),sysApp.assignments.get(lastAss).timeRegisters.size());
	}
	
	
	/*
	 * The user tries manually register a task he/she is not working on
	 */
	
	@Test
	public void registerDailyTimeAlt1() throws WrongInputException{
		Employee employee = sysApp.employees.get(sysApp.assignments.size()-1);
		CalDay day = new CalDay(new CalWeek(2000,2),1);
		sysApp.currentEmp=employee;
		
		assertNotNull(employee);
		assertNotNull(day);
		
		//This should be false since employee 9 not works on task 0. (see sampledatasetup)
		assertFalse(sysApp.registerWorkManually(0, 9, 11, day));
		
	}
	
	/*
	 * Test that employee gets an empty array if the day is not today
	 */
	@Test
	public void registerDailyTimeDayNotToday(){
		Employee employee = sysApp.employees.get(sysApp.assignments.size()-1);
		CalDay day = new CalDay(new CalWeek(2050,2),5);
		sysApp.currentEmp=employee;
		
		assertNotNull(employee);
		assertNotNull(day);

		assertEquals(sysApp.employeesTodaysBookings(employee,day).mainInfo.size(),0);
	}
	
}
