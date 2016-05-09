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
 * Test for user case Seek Assistance
 * @author Benjamin
 * All alternative scenarios is tested
 */

public class SeekAssistanceTest extends SampleDataSetupTest{

	/*
	 * Main scenario: 
	 * The user asks a co-worker for help.
	 * The co-worker is available
	 * The user creates a booking in his/hers calender under his own assignment.
	 */
	
	@Test
	public void seekAssistanceTestMain() throws WrongInputException{
		Employee employee=database.getEmployee(1);
		Employee coWorker=database.getEmployee(2);
		
		CalDay day=new CalDay(new CalWeek(2000,2),1);
		WorkPeriod period = new WorkPeriod(day,13,15);
		
		assertNotNull(employee);
		assertNotNull(coWorker);
		
		//since employee 2 doesn't have any booking between 13 and 15 he should be available.
		assertTrue(coWorker.isAvailable(period, database));
		
		sysApp.seekAssistance(coWorker.ID,1, 2000, 2, 1,13,15);
		
		//checks that the booking is created.
		assertNotNull(database.assignments.get(database.assignments.size()-1));
		//checks that the taskID's are the same.
		assertEquals(database.assignments.get(database.assignments.size()-1).taskID,database.assignments.get(1).taskID);
		//checks that the employeeID's are different, to make sure that it's not just a copy of the other assignment.
		assertFalse(database.assignments.get(database.assignments.size()-1).employeeID==database.assignments.get(1).employeeID);
		
	}
	/*
	 * Alternative scenario 1 
	 * The co-worker does not exist. 
	 * 
	 */
	@Test
	public void seekAssistanceTestAlt1() throws WrongInputException{
		Employee employee=database.getEmployee(1);
		
		CalDay day=new CalDay(new CalWeek(2000,2),1);
		WorkPeriod period = new WorkPeriod(day,9,11);
		
		assertNotNull(employee);
		
		
		try{
			sysApp.seekAssistance(-1,3,2000,2,1,9,11);
			Assert.fail();
		} catch(WrongInputException e){
			
		}
	}
	
	/*
	 * Alternative scenario 2 
	 * The co-worker isn't available. 
	 */
	@Test
	public void seekAssistanceTestAlt2() throws WrongInputException{
		Employee employee=database.employees.get(1);
		Employee coWorker=database.employees.get(2);
		
		CalDay day=new CalDay(new CalWeek(2000,2),1);
		WorkPeriod period = new WorkPeriod(day,13,15);
		Assignment coWorkerAss = new Assignment(database.getTask(1),coWorker);
		database.assignments.add(coWorkerAss);
		WorkPeriod newBooking = new WorkPeriod(day,13,15);
		coWorkerAss.bookings.add(newBooking);
		
		assertNotNull(employee);
		assertNotNull(coWorker);
		
		assertFalse(coWorker.isAvailable(period, database));
		
		try{
			sysApp.seekAssistance(coWorker.ID,3,2000,2,1,13,15);
			Assert.fail();
		} catch(WrongInputException e){
		}
	}
}
