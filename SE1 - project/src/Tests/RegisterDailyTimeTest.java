package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Enums.*;

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
	public void registerDailyTimeMain() throws WrongInputException{
		Employee employee = database.getEmployee(9);
		CalDay day = new CalDay(new CalWeek(2000,2),1);
		int lastAss=database.assignments.size()-1;
		sysApp.logIn(employee.ID);
		assertNotNull(employee);
		assertNotNull(day);
		
		MyMap todaysBookings = employee.dayBookings(day, database);
		
		for(int i=0;i<todaysBookings.mainInfo.size();i++){
			sysApp.copyBookingToTimeRegister((WorkPeriod)todaysBookings.mainInfo.get(i),(Assignment)todaysBookings.secondaryInfo.get(i));
		}
	
		//test that all bookings has been moved to the last assignments timeregister, since we in data setup 
		//made all bookings for emp 9 in the last assignment. 
		assertEquals(todaysBookings.mainInfo.size(),database.assignments.get(lastAss).timeRegisters.size());
	}
	
	
	/*
	 * The user tries manually register a task he/she is not working on
	 */
	
	@Test
	public void registerDailyTimeAlt1() throws WrongInputException{
		Employee employee = database.employees.get(8);
		CalDay day = new CalDay(new CalWeek(2000,2),1);
		sysApp.logIn(employee.ID);
		
		assertNotNull(employee);
		assertNotNull(day);
		
		try {
			sysApp.registerWorkManually(3, 9, 11, day);
			Assert.fail();
		} catch (WrongInputException e) {
		};
		
	}
	
	/*
	 * Test that employee gets an empty array if the day is not today
	 * and tries to register bookings for that day
	 */
	@Test
	public void registerDailyTimeDayNotToday() throws WrongInputException{
		Employee employee = database.employees.get(9);
		CalDay day = new CalDay(new CalWeek(2050,2),5);
		sysApp.currentEmp=employee;
		int lastAss=database.assignments.size()-1;
		assertNotNull(employee);
		assertNotNull(day);

		assertEquals(employee.dayBookings(day, database).mainInfo.size(),0);
		
		MyMap todaysBookings = employee.dayBookings(day, database);
		
		assertEquals(todaysBookings.mainInfo.size(),0);
		
		//since todays bookings is 0, the following for loop should not be entered
		for(Object object: todaysBookings.mainInfo){
			WorkPeriod booking=(WorkPeriod) object;
			sysApp.copyBookingToTimeRegister(booking,database.assignments.get(lastAss));
		}
		
		assertEquals(todaysBookings.mainInfo.size(),database.assignments.get(lastAss).timeRegisters.size());
		
	}
	
}
