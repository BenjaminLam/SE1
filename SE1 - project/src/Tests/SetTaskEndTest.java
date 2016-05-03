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
/** Tests for setTaskend
 * Author Asbjørn
 */
public class SetTaskEndTest extends SampleDataSetup0 {
	
	@Test
	public void setTaskEndMain(){
		//Initialises objects
		Employee employee=super.sysApp.employees.get(0);
		Project project=super.sysApp.projects.get(0);
		Task task1 = sysApp.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016, 20);
		
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskEnd(calWeek1, employee, super.sysApp);
		} catch (WrongInputException e) {
			Assert.fail ();
		}
		assertEquals(task1.end.year, calWeek1.year);
		assertEquals(task1.end.week, calWeek1.week);
	}
	
	/*
	 * Alternative scenario 1
	 * Employee is not projectleader
	 * The task end date 
	 */
	@Test
	public void setTaskEndAlt1(){
		Employee employee=super.sysApp.employees.get(1);
		Project project=super.sysApp.projects.get(0);
		Task task1 = sysApp.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		
		assertFalse(project.isProjectLeader(employee));
		
		try {
			
			task1.setTaskEnd(calWeek1, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e){}
		assertNull(task1.end);
	}
	
	/*
	 * Alternative scenario 2a
	 * The selected date is before task start
	 * End date of task is not changed
	 */
	@Test
	public void setTaskEndAlt2a(){
		Employee employee=super.sysApp.employees.get(0);
		Project project=super.sysApp.projects.get(0);
		Task task1 = sysApp.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek calWeek2 = new CalWeek(2016,32);
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskStart(calWeek1, employee, super.sysApp);		
		} catch (WrongInputException e) { 
			Assert.fail();
		}	
		try {
			task1.setTaskEnd(calWeek2, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e){};
		assertNull(task1.end);
		
		CalWeek calWeek3 = new CalWeek(2016, 45);
		
		try {
			task1.setTaskEnd(calWeek3, employee, super.sysApp);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task1.end, calWeek3);
		}
		
	
	/*
	 * Alternative scenario 2b
	 * Selected date is after project end week
	 * Task end date is not changed
	 */
	@Test
	public void setTaskEndAlt2b(){
		Employee employee=super.sysApp.employees.get(0);
		Project project=super.sysApp.projects.get(0);
		Task task1 = sysApp.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek calWeek2 = new CalWeek(2016,44);
		project.end = calWeek1;
		
		assertTrue(project.isProjectLeader(employee));
		assertEquals(project.end,calWeek1);
		
		try {
			task1.setTaskEnd(calWeek2, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertNull(task1.end);
		
		CalWeek calWeek3 = new CalWeek(2016,42);
		try {
			task1.setTaskEnd(calWeek3, employee, super.sysApp);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task1.end,calWeek3);
	}
	
	/*
	 * Alternative scenario 2c
	 * The selected date is before current date
	 * Task end date is not changed
	 */
	
	@Test
	public void setTaskEndAlt2c() {
		Employee employee=super.sysApp.employees.get(0);
		Project project=super.sysApp.projects.get(0);
		Task task1 = sysApp.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek wrongWeek1 = new CalWeek(2015,32);
		assertTrue(project.isProjectLeader(employee));

		try {
			task1.setTaskEnd(calWeek1, employee, super.sysApp);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(task1.end.year);
		assertNotNull(task1.end.week);
		try {
			task1.setTaskEnd(wrongWeek1, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(task1.end, calWeek1);
		}
	
	
	/* 
	 * Alternative scenario 2d
	 * Selected date is before start date of project
	 * Task end date is not changed
	 */
	@Test
	public void setTaskEndAlt2d(){
		Employee employee=super.sysApp.employees.get(0);
		Project project=super.sysApp.projects.get(0);
		Task task1 = sysApp.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek calWeek2 = new CalWeek(2016,42);
		project.start = calWeek1;
		
		assertTrue(project.isProjectLeader(employee));
		assertEquals(project.start,calWeek1);
		
		try {
			task1.setTaskEnd(calWeek2, employee, super.sysApp);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertNull(task1.end);
		CalWeek calWeek3 = new CalWeek(2016,44);
		try {
			task1.setTaskEnd(calWeek3, employee, super.sysApp);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(task1.end, calWeek3);
	}
	
	
}

