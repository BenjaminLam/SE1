package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Errors.*;
import project.*;

public class AditionalTests extends SampleDataSetup0 {
	//requires employee.getAvailableHours(start,end)
	
	/*
	 * excisting calWeek (start) is inputted
	 * excisting calWeek (end) is inputted
	 * method returns number of hours the person is booked in the interval
	 */
	
	@Test void hoursBookedTest() {
		Employee employee=database.employees.get(0);
		CalWeek start=new CalWeek(2000,1);
		CalWeek end=new CalWeek(2000,4);
		
		double hoursBooked=0;
		for (Assignment assignment:database.assignments) {
			if (assignment.employeeID==employee.ID) {
				for (WorkPeriod wp:assignment.bookings) {
					if (wp.isAfter(start) && wp.isBefore(end)) {
						hoursBooked+=wp.getLength();
					}
				}
			}
		}
		assertEquals(hoursBooked,database.hoursBooked(employee,start,end));
	}
	
	/*
	 * excisting calWeek (start) is inputted
	 * excisting calWeek (end) is inputted
	 * method returns number of hours the person is available in the interval
	 */
	
	@Test
	public void hoursAvailableTest() {
		
		
		
		
		//datastructure?
	}
}
