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
 *
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
		
		assertNotNull(employee);
		assertNotNull(day);
		
		List<WorkPeriod> todaysBookings = database.employeesTodaysBookings(employee, day);
		for(WorkPeriod booking: todaysBookings){
			database.registerBooking(booking,database.assignments.get(9));
			
		}
		
		assertEquals(todaysBookings.size(),database.assignments.get(9).timeRegisters.size());
		
	}
	
	
	@Test
	public void registerDailyTimeAlt1(){
		
	}
	
}
