package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import Exceptions_Enums.*;
import project.*;

public class AditionalTests extends SampleDataSetupTest {
	/*
	 * Existing calWeek (start) is inputed
	 * Existing calWeek (end) is inputed
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
	
	/*
	 * Test for copyBookingTimeRegister
	 * 
	 */
	@Test
	public void copyBookingToTimeRegisterTest() throws WrongInputException {
		CalWeek week = new CalWeek(2016, 34);
		CalDay day = new CalDay(week,5);
		WorkPeriod workPeriod1 = new WorkPeriod(day, 8, 10);
		
		Assignment assignment = new Assignment(database.getTask(1), database.getEmployee(1));
		Assignment assignment1 = new Assignment(database.getTask(2), database.getEmployee(2));
		int size = assignment.timeRegisters.size();
		assertNotNull(assignment.timeRegisters);
		assertNotNull(workPeriod1);
		try {
			sysApp.copyBookingToTimeRegister(workPeriod1, assignment);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotEquals(size, assignment.timeRegisters.size());
		
		try {
			sysApp.copyBookingToTimeRegister(null, assignment1);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(size, assignment1.timeRegisters.size());
		try {
			sysApp.copyBookingToTimeRegister(workPeriod1, null);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(size, assignment1.timeRegisters.size());
	}
	
	/*
	 * Test for setProjectLeader
	 */
	@Test
	public void setProjectLeaderTest(){
		Project project = super.database.getProject(2);
		Employee employee = super.database.getEmployee(3);
		try {
			sysApp.logIn(employee.ID);
		} catch (WrongInputException e1) {
			Assert.fail();
		}
		Employee employee2 = database.getEmployee(7);
		assertFalse(project.isProjectLeader(employee));
		
		try {
			sysApp.setProjectLeader(project.ID, employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		
		assertTrue(project.isProjectLeader(employee));
		
		try {
			sysApp.setProjectLeader(-1, employee2.ID);
			Assert.fail();
		} catch (WrongInputException e) {}
		assertFalse(project.isProjectLeader(employee2));
		
		try {
			sysApp.setProjectLeader(project.ID, -1);
			Assert.fail();
		} catch (WrongInputException e){}
		assertTrue(project.isProjectLeader(employee));
		
		try {
			sysApp.setProjectLeader(project.ID, employee2.ID);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertTrue(project.isProjectLeader(employee2));
		try {
			sysApp.setProjectLeader(project.ID, employee.ID);
				Assert.fail();
			} catch (WrongInputException e){}
		assertFalse(project.isProjectLeader(employee));
		}
	
	
}

