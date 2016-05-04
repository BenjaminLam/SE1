package Tests;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import Exceptions_Errors.*;
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
	public void setTaskStartMain(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		assertNotNull(calWeek1.week);
		assertNotNull(calWeek1.year);
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskStart(calWeek1, employee, super.sysApp);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(task1.start.week,calWeek1.week);
		assertEquals(task1.start.year,calWeek1.year);
	}
	/*
	 * Alternative scenario 1
	 * Employee is not projectleader
	 * Task start date is not changed
	 */
	@Test
	public void setTaskAlt1(){
		Employee employee=super.database.employees.get(1);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		assertFalse(project.isProjectLeader(employee));
		try {
			task1.setTaskStart(calWeek1, employee, sysApp);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(task1.start);
		
	}
	/* 
	 * Alternative scenario 2a
	 * The selected date is in the past
	 * Method returns error
	 */
	@Test
	public void setTaskStartAlt2a(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek wrongWeek1 = new CalWeek(2015,32);
		
		assertTrue(project.isProjectLeader(employee));
		
		assertNull(task1.start);
		try {
			task1.setTaskStart(calWeek1, employee, super.sysApp);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(task1.start.year);
		assertNotNull(task1.start.week);
		try {
			task1.setTaskStart(wrongWeek1, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(task1.start, calWeek1);
	}
	
	/*
	 * Alternative scenario 2b
	 * Selected date is before start date of project
	 */
	@Test
	public void setTaskStartAlt2b(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		project.start = calWeek1;
		CalWeek wrongWeek1 = new CalWeek(2016,42);
		
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskStart(wrongWeek1, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e){}
		assertNull(task1.start);
		
		CalWeek calWeek3 = new CalWeek(2016,44);
		try {
			task1.setTaskStart(calWeek3, employee, super.sysApp);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task1.start,calWeek3);
	}
	
	/*
	 * Alternative scenario 2c
	 * Selected date is after project end date
	 */
	@Test
	public void setTaskStartAlt2c(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		project.end = calWeek1;
		CalWeek wrongWeek1 = new CalWeek(2016,44);
		
		assertTrue(project.isProjectLeader(employee));
		assertNotNull(project.end);
		
		try {
			task1.setTaskStart(wrongWeek1, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
		assertNull(task1.start);
		CalWeek calWeek3 = new CalWeek(2016, 41);
		try {
			task1.setTaskStart(calWeek3, employee, super.sysApp);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task1.start, calWeek3);
	}
	
	/*
	 * Alternative scenario 2d
	 * The selected date is after task end date
	 */
	@Test
	public void setTaskStartAlt2d(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek wrongWeek1 = new CalWeek(2016,44);
		
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskEnd(calWeek1, employee, super.sysApp);
		} catch (WrongInputException e1) {
			Assert.fail();
		}
		try {
			task1.setTaskStart(wrongWeek1, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(task1.start);
		assertEquals(task1.end,calWeek1);
		CalWeek calWeek3 = new CalWeek(2016,41);
		try {
			task1.setTaskStart(calWeek3, employee, super.sysApp);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task1.start, calWeek3);
	}
	
}
