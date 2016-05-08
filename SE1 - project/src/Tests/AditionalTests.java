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
	 * Test for createProject
	 */
	@Test
	public void createProjectTest() {
		Project project1 = database.getProject(1);
		project1.name = "Programming";
		int size = database.projects.size();
		try {
			sysApp.createProject("Programming");
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(size, database.projects.size());
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
	
	/*
	 * Test for removeEmployee
	 */
	@Test
	public void removeEmployeeTest() {
		Employee employee = database.getEmployee(0);
		Employee employee1 = database.getEmployee(1);
		assertNotNull(employee);
		assertNotNull(employee1);
		try {
			sysApp.logIn(employee1.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		try {
			sysApp.removeEmployee(employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertFalse(database.employees.contains(employee));
		
		try {
			sysApp.removeEmployee(employee1.ID);
			Assert.fail();
		} catch (WrongInputException e){}
	}
	
	/*
	 * Test for renameProject
	 */
	@Test
	public void renameProjectTest(){
		Project project = database.getProject(1);
		String name = "projectID";
		
		try {
			sysApp.renameProject(project.ID, name);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(project.name, name);
		
		Project project1 = database.getProject(2);
		project1.name = "project1";
		
		try {
			sysApp.renameProject(project.ID, "project1");
			Assert.fail();
		} catch (WrongInputException e){}
		assertNotEquals("project1", project.name);
		
		try {
			sysApp.renameProject(-1, name);
			Assert.fail();
		} catch (WrongInputException e){}
		assertEquals(project.name, name);
	}
	
	/*
	 * Test for removeProject
	 */
	@Test
	public void removeProject() {
		Project project = database.getProject(1);
		Employee employee = database.getEmployee(0);
		int size = database.projects.size();
		try {
			sysApp.logIn(employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(sysApp.currentEmp, employee);
		
		try {
			sysApp.removeProject(project.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotEquals(size, database.projects.size());
		Employee employee2 = database.getEmployee(3);
		int size1 = database.projects.size();
		try {
			sysApp.logIn(employee2.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(sysApp.currentEmp, employee2);	
		try {
			sysApp.removeProject(project.ID);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(size1, database.projects.size());
		
	}
}

