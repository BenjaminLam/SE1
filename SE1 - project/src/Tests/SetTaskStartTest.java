package Tests;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import Exceptions_Enums.*;
import project.*;
/** Tests for setTaskStart
 * Author Asbjørn
 */
public class SetTaskStartTest extends SampleDataSetupTest {
	/*
	 * Main scenario
	 * Projectleader tries to change the start date of a task.
	 * The Start date is changed.
	 */
	@Test
	public void setTaskStartMain() throws WrongInputException{
		Project project1 = database.getProject(1);
		Employee employee = database.getEmployee(0);
		sysApp.logIn(employee.ID);
		
		assertNotNull(project1);
		
		assertTrue(project1.isProjectLeader(employee));
		try {
			sysApp.createTask(project1.ID, "task1");
		} catch (WrongInputException e1) {
			Assert.fail();
		}
		
		try {
			sysApp.setTaskStart(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(database.getTask(1).start.year, 2016);
		assertEquals(database.getTask(1).start.week, 43);
	}
	/*
	 * Alternative scenario 1
	 * Employee is not projectleader
	 * Task start date is not changed
	 */
	@Test
	public void setTaskAlt1() throws WrongInputException{
		Employee employee=database.getEmployee(1);
		Project project=database.getProject(1);
		sysApp.logIn(employee.ID);
		assertFalse(project.isProjectLeader(employee));
		
		try {
			sysApp.setTaskStart(1, 2016, 43);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(database.getTask(1).start);
		
	}
	/* 
	 * Alternative scenario 2a
	 * The selected date is in the past
	 * Method returns error
	 */
	@Test
	public void setTaskStartAlt2a() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		Project project=database.getProject(1);
		sysApp.logIn(employee.ID);
		assertTrue(project.isProjectLeader(employee));
		
		try {
			sysApp.setTaskStart(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(database.getTask(1).start);
		try {
			sysApp.setTaskStart(1, 2015, 2);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(database.getTask(1).start.year, 2016);
		assertEquals(database.getTask(1).start.week, 43);
		
	}
	
	/*
	 * Alternative scenario 2b
	 * Selected date is before start date of project
	 */
	@Test
	public void setTaskStartAlt2b() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		Project project=database.getProject(0);
		sysApp.logIn(employee.ID);
		
		assertTrue(project.isProjectLeader(employee));
		project.start.week = 36;
		project.start.year = 2016;
		try {
			sysApp.setTaskStart(1, 2016, 3);
			Assert.fail();
		} catch (WrongInputException e){}
		assertNull(database.getTask(1));
		
		try {
			sysApp.setTaskStart(1, 2016, 38);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getTask(1).start.year, 2016);
		assertEquals(database.getTask(1).start.week, 38);
	}
	
	/*
	 * Alternative scenario 2c
	 * Selected date is after project end date
	 */
	@Test
	public void setTaskStartAlt2c() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		Project project=database.getProject(1);
		sysApp.logIn(employee.ID);
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
		assertEquals(database.getTask(0).start.year, 2016);
		assertEquals(database.getTask(0).start.week, 42);
	}
	
	/*
	 * Alternative scenario 2d
	 * The selected date is after task end date
	 */
	@Test
	public void setTaskStartAlt2d() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		Project project=database.getProject(0);
		sysApp.logIn(employee.ID);
		
		assertTrue(project.isProjectLeader(employee));
		
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

}
