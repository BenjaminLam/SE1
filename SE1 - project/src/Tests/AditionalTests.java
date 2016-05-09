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
	/*
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
		assertEquals(hoursBooked,employee.hoursBooked(database,start,end),0.1);
	}
	
	/*
	 * Existing calWeek (start) is inputed
	 * Existing calWeek (end) is inputed
	 * method returns number of hours the person is available in the interval
	 */
	/*
	@Test
	public void hoursAvailableTest() {
		CalWeek start=new CalWeek(2000,1);
		CalWeek end=new CalWeek(2000,4);
		
		//expected 37.5*4 since there's 4 week and employee0 doesn't have any booking with time in it
		double expected=37.5*4;
		
		Employee employee=database.employees.get(0);
		
		double calculated=sysApp.hoursAvailable(employee, start, end);
		
		assertEquals(expected,calculated,0.1);
	}
	/*
	 * Test for createProject
	 */
	/*
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
		project.projectLeader = employee;
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
		
		project.projectLeader = null;
		try {
			sysApp.setProjectLeader(project.ID, employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
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
		assertFalse(project.isProjectLeader(employee2));
		try {
			sysApp.removeProject(project.ID);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(size1, database.projects.size());
		try {
			sysApp.removeProject(-1);
			Assert.fail();
		} catch (WrongInputException e) {
		}
	}
	
	/*
	 * Test for removeTask
	 */
	@Test
	public void removeTaskTest(){
		Task task = database.getTask(1);
		int size = database.numberOfTasks();
		try {
			sysApp.removeTask(task.ID);
		} catch (WrongInputException e) {
			Assert.fail();	
		}
		assertNotEquals(size, database.numberOfTasks());
		
		int size1 = database.numberOfTasks();
		try {
			sysApp.removeTask(-1);
			Assert.fail();
		} catch (WrongInputException e){}
		assertEquals(size1, database.numberOfTasks());	
		
	}
	
	/*
	 * Test for register sickness
	 */
	@Test
	public void registerSicknessTest(){
		Employee employee = database.getEmployee(0);
		try {
			sysApp.logIn(employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		try {
			sysApp.registerSickness();
		} catch (WrongInputException e) {
			Assert.fail();
		}
		try {
			assertTrue(employee.isSick(database, employee));
		} catch (WrongInputException e) {
			Assert.fail();
		}
		
	}
	/*
	 * Test for register vacation
	 */
	@Test
	public void registerVacationTest() throws WrongInputException{
		Employee employee = database.getEmployee(1);
		CalWeek week1 = new CalWeek(2016,31);
		CalWeek week2 = new CalWeek(2016,33);
		CalDay day1 = new CalDay(week1, 3);
		CalDay day2 = new CalDay(week2, 3);
		
		sysApp.currentEmp = employee;
		
		try {
			sysApp.registerVacation(2016, 31 ,3 , 2016, 33, 3);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		
		try {
			sysApp.registerVacation(2016, 31, 3, 2016, 33, 3);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		
		try {
			assertTrue(employee.isOnVacation(database, employee, day1));
		} catch (WrongInputException e) {
			Assert.fail();		
		}
		
		try {
			sysApp.registerVacation(2016, 33, 3, 2016, 31, 3);
			Assert.fail();
		} catch (WrongInputException e){}
	}
	/*
	 * Test for register course
	 */
	@Test
	public void registerCourseTest() throws WrongInputException{
		Employee employee = database.getEmployee(1);
		CalWeek week1 = new CalWeek(2016,31);
		CalWeek week2 = new CalWeek(2016,33);
		CalDay day1 = new CalDay(week1, 3);
		CalDay day2 = new CalDay(week2, 5);
		
		sysApp.currentEmp = employee;
		
		try {
			sysApp.registerCourse(2016, 31, 3, 2016, 33, 5);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		
		try {
			sysApp.registerCourse(2016, 33, 5, 2016, 31, 3);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
		
		
		assertTrue(employee.isOnCourse(database, employee, day1));

	}
	/*
	 * Test for setTaskBudgetTime
	 */
	
	@Test
	public void setTaskBudgetTimeTest(){
		Employee employee = database.getEmployee(1);
		sysApp.currentEmp = employee;
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader = employee;
		double budget = 17.5; 
		try {
			sysApp.setTaskBudgetTime(task.ID, budget);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertNotNull(task.timeBudget);
		
		Employee employee1 = database.getEmployee(0);
		sysApp.currentEmp = employee1;
		
		try {
			sysApp.setTaskBudgetTime(task.ID, budget);
			Assert.fail();
		} catch (WrongInputException e){}
		
		try {
			sysApp.setTaskBudgetTime(-1, budget);
			Assert.fail();
		} catch (WrongInputException e){}
	}
	
	/*
	 * Test for renameTask
	 */
	@Test
	public void renameTaskTest(){
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		Employee employee = database.getEmployee(1);
		project.projectLeader = employee;
		sysApp.currentEmp = employee;
		String name = "projectnavn";
		assertNotEquals(task.name, name);
		try {
			sysApp.renameTask(task.ID, name);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task.name, name);
		//Testing for task = null
		try {
			sysApp.renameTask(-1, name);
			Assert.fail();
		} catch (WrongInputException e){}
		
		//Testing for currentEmp not projectleader
		Employee employee1 = database.getEmployee(2);
		sysApp.currentEmp = employee1;
		assertFalse(project.isProjectLeader(employee1));
		try {
			sysApp.renameTask(task.ID, name);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertEquals(task.name, name);
	}
	
	/*
	 * Test for createBooking
	 */
	@Test
	public void createBookingTest(){
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		Employee employee = database.getEmployee(1);
		project.projectLeader = employee;
		sysApp.currentEmp = employee;
		Employee employee1 = database.getEmployee(2);
		int size = database.getAssignment(task.ID, employee1.ID).bookings.size();
		try{
			sysApp.createBooking(employee1.ID, task.ID, 2016, 34, 3, 9.30, 12.30);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertNotEquals(size, database.getAssignment(task.ID, employee1.ID).bookings.size());
		//Employee not projectleader
		sysApp.currentEmp = employee1;
		int size1 = database.getAssignment(task.ID, employee.ID).bookings.size();
		try{
			sysApp.createBooking(employee.ID, task.ID, 2016, 34, 3, 9.30, 12.30);
			Assert.fail();
		} catch (WrongInputException e){
		}
		assertEquals(size1, database.getAssignment(task.ID, employee.ID).bookings.size());
		
		// Task is null
		sysApp.currentEmp = employee;
		try{
			sysApp.createBooking(employee.ID, -1, 2016, 34, 3, 9.30, 12.30);
			Assert.fail();
		} catch (WrongInputException e){
		}
		assertEquals(size1, database.getAssignment(task.ID, employee.ID).bookings.size());
		
		//Employee is null
		try{
			sysApp.createBooking(-1, task.ID, 2016, 34, 3, 9.30, 12.30);
			Assert.fail();
		} catch (WrongInputException e){
		}
	}
	
	/*
	 * Test for removeBooking
	 */
	@Test
	public void removeBookingTest() {
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		Employee employee = database.getEmployee(1);
		project.projectLeader = employee;
		sysApp.currentEmp = employee;
		Employee employee1 = database.getEmployee(2);
		int size = database.getAssignment(task.ID, employee1.ID).bookings.size();
		
		try{
			sysApp.createBooking(employee1.ID, task.ID, 2016, 34, 3, 9.30, 12.30);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertNotEquals(size, database.getAssignment(task.ID, employee1.ID).bookings.size());
		
		//Employee is not projectleader
		sysApp.currentEmp = employee1;
		
		try{
			sysApp.removeBooking(employee1.ID, task.ID, 2016, 34, 3, 9.30, 12.30);
			Assert.fail();
		} catch (WrongInputException e){
		}
		assertNotEquals(size, database.getAssignment(task.ID, employee1.ID).bookings.size());
		
		//Task is null
		sysApp.currentEmp = employee;
		try{
			sysApp.removeBooking(employee1.ID, -1, 2016, 34, 3, 9.30, 12.30);
			Assert.fail();
		} catch (WrongInputException e){
		}
		assertNotEquals(size, database.getAssignment(task.ID, employee1.ID).bookings.size());
		
		//Employee is null
		
		try{
			sysApp.removeBooking(-1, task.ID, 2016, 34, 3, 9.30, 12.30);
			Assert.fail();
		} catch (WrongInputException e){
		}
		assertNotEquals(size, database.getAssignment(task.ID, employee1.ID).bookings.size());
		
		try{
			sysApp.removeBooking(employee1.ID, task.ID, 2016, 34, 3, 9.30, 12.30);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(size, database.getAssignment(task.ID, employee1.ID).bookings.size());
	}
	
	
	/*
	 * Test for setProjectStart
	 */
	
	@Test
	public void setProjectStartTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		
		assertNotNull(project);
		
		assertTrue(project.isProjectLeader(employee));	
				
		try {
			sysApp.setProjectStart(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(database.getProject(1).start.year, 2016);
		assertEquals(database.getProject(1).start.week, 43);
	}
	/*
	 * Alternative scenario 1
	 * Employee is not projectleader
	 * Task start date is not changed
	 */
	@Test
	public void setProjectStartAlt1Test() throws WrongInputException{
		Employee employee=database.getEmployee(1);
		Project project=database.getProject(1);
		sysApp.logIn(employee.ID);
		assertFalse(project.isProjectLeader(employee));
		
		try {
			sysApp.setProjectStart(1, 2016, 43);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(database.getProject(1).start);
		
	}
	/* 
	 * Alternative scenario 2a
	 * The selected date is in the past
	 * Method returns error
	 */
	@Test
	public void setProjectStartAlt2aTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		
		try {
			sysApp.setProjectStart(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(database.getProject(1).start);
		try {
			sysApp.setProjectStart(1, 2015, 2);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(database.getProject(1).start.year, 2016);
		assertEquals(database.getProject(1).start.week, 43);
		
	}

	/*
	 * Alternative scenario 2b
	 * Selected date is before start date of a task
	 */
	/*
	@Test
	public void setProjectStartAlt2bTest() throws WrongInputException{
		Employee employee=database.getEmployee(3);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(3);
		Project project = database.getProject(4);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		
		
		task.start = new CalWeek(2016, 38);

		try {
			sysApp.setProjectStart(4, 2016, 37);
			Assert.fail();
		} catch (WrongInputException e){	
		}
		try {
			sysApp.setProjectStart(1, 2016, 38);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getProject(1).start.year, 2016);
		assertEquals(database.getProject(1).start.week, 38);
	}
	
	/*
	 * Alternative scenario 2c
	 * Selected date is after project end date
	 */
	@Test
	public void setProjectStartAlt2cTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		CalWeek calWeek1 = new CalWeek(2016,43);
		project.end = calWeek1;
		
		assertTrue(project.isProjectLeader(employee));
		assertNotNull(project.end);
		
		try {
			sysApp.setTaskStart(1, 2016, 44);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
		assertNull(database.getProject(0).start);
		
		try {
			sysApp.setTaskStart(1, 2016, 42);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getTask(1).start.year, 2016);
		assertEquals(database.getTask(1).start.week, 42);
	}
	
	/*
	 * Alternative scenario 2d
	 * The selected date is after task end date
	 */
	@Test
	public void setProjectStartAlt2dTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setTaskEnd(1, 2016, 42);
		} catch (WrongInputException e1) {
			Assert.fail();
		}
		try {
			sysApp.setTaskStart(1, 2016, 43);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(database.getTask(1).start);
		assertNotNull(database.getTask(1).end);
		
		try {
			sysApp.setTaskStart(1, 2016, 41);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getTask(1).start.year, 2016);
		assertEquals(database.getTask(1).start.week, 41);
	}
	/*
	 * Alternative 3 - Project is null
	 */
	@Test
	public void setProjectStartAlt3Test(){
		Employee employee=database.getEmployee(0);
		try {
			sysApp.logIn(employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setTaskStart(-2, 2016, 44);
			Assert.fail();
		} catch (WrongInputException e) {
		}
	}
	
	/*
	 * Tests for setProjectEnd
	 */
	@Test
	public void setProjectEndMainTest() throws WrongInputException{
		
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		//Task task = database.getTask(1);
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		//assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setProjectEnd(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail ();
		}
		assertEquals(database.getProject(1).end.year, 2016);
		assertEquals(database.getProject(1).end.week, 43);
	}
	
	/*
	 * Alternative scenario 1
	 * Employee is not projectleader
	 * The project end date is not changed 
	 */	
	@Test
	public void setProjectEndAlt1Test() throws WrongInputException{
		Employee employee=database.getEmployee(1);
		Project project=database.getProject(1);
		sysApp.logIn(employee.ID);
		
		assertFalse(project.isProjectLeader(employee));
		
		try {
			sysApp.setProjectEnd(1, 2016, 43);
			Assert.fail();
		} catch (WrongInputException e){}
		assertNull(database.getProject(1).end);
	}
	
	/*
	 * Alternative scenario 2a
	 * The selected date is before Project start
	 * End date of project is not changed
	 */
	
	@Test
	public void setProjectEndAlt2aTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		Task task = database.getTask(1);
	
		try {
			sysApp.setProjectStart(1, 2016, 43);		
		} catch (WrongInputException e) { 
			Assert.fail();
		}	
		
		try {
			sysApp.setProjectEnd(1, 2016, 42);
			Assert.fail();
		} catch (WrongInputException e){};
		assertNull(database.getProject(1).end);
		
		try {
			sysApp.setProjectEnd(1, 2016, 44);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getProject(1).end.year, 2016);
		assertEquals(database.getProject(1).end.week, 44);
		}
		
	
	/*
	 * Alternative scenario 2b
	 * Selected date is before a tasks end week
	 * project end date is not changed
	 *
	@Test
	public void setProjectEndAlt2bTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		//Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		//assertEquals(database.getProject(1), project);
		CalWeek calWeek1 = new CalWeek(2016,43);
		project.end = calWeek1;
		
		assertTrue(project.isProjectLeader(employee));
		assertEquals(project.end,calWeek1);
		
		try {
			sysApp.setProjectEnd(1, 2016, 42);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertNull(database.getProject(1).end);
		
		try {
			sysApp.setProjectEnd(1, 2016, 44);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getProject(1).end.year, 2016);
		assertEquals(database.getProject(1).end.week, 44);
	}
	*/
	/*
	 * Alternative scenario 2c
	 * The selected date is before current date
	 * Task end date is not changed
	 */
	
	@Test
	public void setProjectEndAlt2cTest() throws WrongInputException {
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		//Task task = database.getTask(1);
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		//assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setProjectEnd(1, 2016, 1);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		
		assertNull(database.getProject(1).end);
		try {
			sysApp.setProjectEnd(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(database.getProject(1).end.year, 2016);
		assertEquals(database.getProject(1).end.week, 43);
		}
	
	
	/* 
	 * Alternative scenario 2d
	 * Selected date is before start date of of a task
	 * Project end date is not changed
	 */
	/*
	@Test
	public void setProjectEndAlt2dTest() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		CalWeek calWeek1 = new CalWeek(2016,43);
		Task task = new Task(project,"fun");
		task.start = calWeek1;
		
		assertTrue(project.isProjectLeader(employee));
		assertEquals(task.start,calWeek1);
		
		try {
			sysApp.setProjectEnd(1, 2016, 42);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertNull(database.getProject(1).end);
		
		try {
			sysApp.setProjectEnd(1, 2016, 44);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getProject(1).end.year, 2016);
		assertEquals(database.getProject(1).end.week, 44);
	}
	*/
	/*
	 * Alternative 3 - Task is null
	 */
	@Test
	public void setTaskEndAlt3() {
		Employee employee=database.getEmployee(0);
		try {
			sysApp.logIn(employee.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		Project project = database.getProject(1);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		
		try {
			sysApp.setProjectEnd(-1, 2016, 44);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		
	}
	
	/*
	 *Test for EmployeesForTask 
	 */
	@Test
	public void employeesForTaskTest() throws WrongInputException{
		Task task = database.getTask(4);
		Employee employee = database.getEmployee(1);
		Project project = database.getProject(task.ID);
		sysApp.currentEmp = employee;
		project.projectLeader = employee;
		assertTrue(project.isProjectLeader(employee));
		task.start = new CalWeek(2016,43);
		task.end = new CalWeek(2016,45);
		try {
			sysApp.employeesForTask(task.ID);
		} catch (WrongInputException e){
			Assert.fail();
		}
		
		
	}
	/*
	 * taskReport Tests
	 */
	
	@Test
	public void taskReportTest () throws WrongInputException {
		Task task = database.getTask(3);
		assertNotNull(task);
		
		int projectID = task.projectID;
		Project project = database.getProject(projectID);
		
		Employee employee=database.employees.get(0);
		
		sysApp.logIn(employee.ID);
		
		assertTrue(project.isProjectLeader(employee));
		
		String[] taskReport=null;
		
		try {
			taskReport = sysApp.createTaskReport(task.ID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(taskReport);	
	}
	
	/*
	 *Alternative scenario1: 
	 * task doesn't exist
	 * employee tries to create report
	 * exception is thrown
	 * report is not created
	 */
	
	@Test
	public void TaskReportAlt1Test () throws WrongInputException {
		Task task = null;
		assertNull(task);
		
		int taskID= -1;
		
		Employee employee=database.employees.get(0);
		
		sysApp.logIn(employee.ID);
		
		String[] taskReport=null;
		
		try {
			taskReport=sysApp.createTaskReport(taskID);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(taskReport);	
	}
	
	/*
	 *Alternative scenario2: 
	 * task exists
	 * employee is not project leader
	 * employee tries to create report
	 * exception is thrown
	 * report is not created
	 */
	
	@Test
	public void taskReportAlt2Test() throws WrongInputException {
		Task task = database.getTask(3);
		assertNotNull(task);
		
		int projectID = task.projectID;
		Project project = database.getProject(projectID);
		
		Employee employee=database.employees.get(2);
		
		sysApp.logIn(employee.ID);
		
		assertFalse(project.isProjectLeader(employee));
		
		String[] taskReport=null;
		
		try {
			taskReport=sysApp.createTaskReport(task.ID);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(taskReport);	
	}
	@Test
	public void isProjectLeader1Test(){
		Employee employee = database.getEmployee(0);
		Project project = database.getProject(1);

		database.isProjectLeader(employee);	
		assertEquals(project.projectLeader, employee);
	}
	
	@Test
	public void isProjectLeader2Test(){
		Employee employee = database.getEmployee(1);
		Project project = database.getProject(1);
		
		database.isProjectLeader(employee);	
		assertNotEquals(project.projectLeader, employee);
	}
	
	@Test
	public void registerWorkManuallyTest() throws WrongInputException{
		Task task = database.getTask(4);
		Employee employee = database.getEmployee(1);
		//Project project = database.getProject(task.projectID);
		sysApp.currentEmp = employee;
		CalWeek week = new CalWeek(2016,44);
		CalDay day = new CalDay(week, 4);
		Employee employee3 = database.getEmployee(2);
		sysApp.manTask(task.ID, employee3.ID);
		
		try {
		sysApp.registerWorkManually(task.ID, 9.5, 13.5, 2016, 44, 4);
		} catch (WrongInputException e){
		Assert.fail();
		}
		//Alternative scenario - Not assigned.
		Employee employee1 = database.getEmployee(4);
		sysApp.currentEmp = employee1;
		
		try {
		sysApp.registerWorkManually(task.ID, 9.5 , 13.5, 2016, 44, 4);
		Assert.fail();
		} catch (WrongInputException e){}
		
	}
	
	@Test
	public void WorkPeriodTests() throws WrongInputException {
		CalDay day = new CalDay(new CalWeek(2000,2),1);		

		try{
			WorkPeriod period1 = new WorkPeriod(day,-1,3);
			Assert.fail();
		} catch (WrongInputException e){
		} 
		
		try{
			WorkPeriod period2 = new WorkPeriod(day,1,0);
			Assert.fail();
		} catch (WrongInputException e){
	
		} 		

		try{
			WorkPeriod period3 = new WorkPeriod(day,24,1);
			Assert.fail();	
		} catch (WrongInputException e){
		} 
		
		try{
			WorkPeriod period5 = new WorkPeriod(day,24,-1);
			Assert.fail();	
		} catch (WrongInputException e){
		} 
		
		try{
			WorkPeriod period6 = new WorkPeriod(day,25,1);
			Assert.fail();	
		} catch (WrongInputException e){
		} 
		
		try{
			WorkPeriod period4 = new WorkPeriod(day,1,25);
			Assert.fail();	
		} catch (WrongInputException e){
		} 

	}
	
	/*
	 * Test for todaysBookings
	 */
	@Test
	public void todaysBookings(){
		Employee employee = database.getEmployee(1);
		sysApp.currentEmp = employee;
		try {
			sysApp.todaysBookings();
		} catch (WrongInputException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void sysAppTest(){
		SysApp sysapp = new SysApp();
	}
	
	@Test
	public void createProjectTest(){
		Project project = database.getProject(1);
		project.name = "project";
		
		try {
			sysApp.createProject("project");
			Assert.fail();
		} catch (WrongInputException e){}
	}
	
	
	/*
	 * employees.hoursRegistredToday
	 */
	@Test
	public void hoursRegistredTodayTest(){
		Employee employee = database.getEmployee(1);
		try {
			employee.hoursRegisteredToday(database);
		} catch (WrongInputException e){
			Assert.fail();
		}
	}
	
	/*
	 * CreateBookingForCoWorkerTest
	 */
	@Test
	public void createBookingForCoWorkerTest() throws WrongInputException{
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		Employee employee = database.getEmployee(1);
		Employee coWorker = database.getEmployee(2);
		project.projectLeader = employee;
		sysApp.currentEmp = employee;
		CalWeek week = new CalWeek(2016,44);
		CalDay day = new CalDay(week, 4);
		WorkPeriod period = new WorkPeriod(day, 10, 12);
		try {
		database.createBookingForCoWorker(coWorker, task, period);
		} catch (WrongInputException e){
			Assert.fail();
		}
		task = null;
		assertNull(task);
		try {
		database.createBookingForCoWorker(coWorker, task, period);
		Assert.fail();
		} catch (WrongInputException e){}
		
		task = database.getTask(1);
		assertNotNull(task);
		coWorker = null;
		assertNull(coWorker);
		try {
		database.createBookingForCoWorker(coWorker, task, period);
		Assert.fail();
		} catch (WrongInputException e){}
	}
}

	


