package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Enums.*;
import project.*;

public class AditionalTests extends SampleDataSetupTest {
	/*
	 * excisting calWeek (start) is inputted
	 * excisting calWeek (end) is inputted
	 * method returns number of hours the person is booked in the interval
	 */
	
	@Test 
	public void hoursBookedTest() {
		Employee employee=database.employees.get(0);
		CalWeek start=new CalWeek(2000,1);
		CalWeek end=new CalWeek(2000,4);
		
		double hoursBooked=0;
		for (Assignment assignment:database.assignments) {
			if (assignment.employeeID==employee.ID) {
				for (WorkPeriod wp:assignment.bookings) {
					if (wp.day.week.isAfter(start) && wp.day.week.isBefore(end)) {
						hoursBooked+=wp.getLength();
					}
				}
			}
		}
		assertEquals(hoursBooked,sysApp.hoursBooked(employee,start,end),0.1);
	}
	
	/*
	 * excisting calWeek (start) is inputted
	 * excisting calWeek (end) is inputted
	 * method returns number of hours the person is available in the interval
	 */
	
	@Test
	public void hoursAvailableTest() {
		CalWeek start=new CalWeek(2000,1);
		CalWeek end=new CalWeek(2000,4);
		
		//expected 37.5*4 since theres 4 week and employee0 doesn't have any booking with time in it
		double expected=37.5*4;
		
		Employee employee=database.employees.get(0);
		
		double calculated=sysApp.hoursAvailable(employee, start, end);
		
		assertEquals(expected,calculated,0.1);
	}
}
