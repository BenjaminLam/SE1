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
/** Tests for setTaskend
 * Author Asbjørn
 */
public class SetTaskEndTest extends SampleDataSetupTest {
	
	@Test
	public void setTaskEndMain() throws WrongInputException{
		
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setTaskEnd(task.ID, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail ();
		}
		assertEquals(database.getTask(1).end.year, 2016);
		assertEquals(database.getTask(1).end.week, 43);
	}
	
	/*
	 * Alternative scenario 1
	 * Employee is not projectleader
	 * The task end date 
	 */	
	@Test
	public void setTaskEndAlt1() throws WrongInputException{
		Employee employee=database.getEmployee(1);
		Project project=database.getProject(1);
		sysApp.logIn(employee.ID);
		
		assertFalse(project.isProjectLeader(employee));
		
		try {
			sysApp.setTaskEnd(1, 2016, 43);
			Assert.fail();
		} catch (WrongInputException e){}
		assertNull(database.getTask(1).end);
	}
	
	/*
	 * Alternative scenario 2a
	 * The selected date is before task start
	 * End date of task is not changed
	 */
	
	@Test
	public void setTaskEndAlt2a() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setTaskStart(1, 2016, 43);		
		} catch (WrongInputException e) { 
			Assert.fail();
		}	
		
		try {
			sysApp.setTaskEnd(1, 2016, 42);
			Assert.fail();
		} catch (WrongInputException e){};
		assertNull(database.getTask(1).end);
		
		try {
			sysApp.setTaskEnd(1, 2016, 44);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getTask(1).end.year, 2016);
		assertEquals(database.getTask(1).end.week, 44);
		}
		
	
	/*
	 * Alternative scenario 2b
	 * Selected date is after project end week
	 * Task end date is not changed
	 */
	@Test
	public void setTaskEndAlt2b() throws WrongInputException{
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
		assertEquals(project.end,calWeek1);
		
		try {
			sysApp.setTaskEnd(1, 2016, 44);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertNull(database.getTask(1).end);
		
		try {
			sysApp.setTaskEnd(1, 2016, 42);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getTask(1).end.year, 2016);
		assertEquals(database.getTask(1).end.week, 42);
	}
	
	/*
	 * Alternative scenario 2c
	 * The selected date is before current date
	 * Task end date is not changed
	 */
	
	@Test
	public void setTaskEndAlt2c() throws WrongInputException {
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setTaskEnd(1, 2016, 1);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		
		assertNull(database.getTask(1).end);
		try {
			sysApp.setTaskEnd(1, 2016, 43);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertEquals(database.getTask(1).end.year, 2016);
		assertEquals(database.getTask(1).end.week, 43);
		}
	
	
	/* 
	 * Alternative scenario 2d
	 * Selected date is before start date of project
	 * Task end date is not changed
	 */
	@Test
	public void setTaskEndAlt2d() throws WrongInputException{
		Employee employee=database.getEmployee(0);
		sysApp.logIn(employee.ID);
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		CalWeek calWeek1 = new CalWeek(2016,43);
		project.start = calWeek1;
		
		assertTrue(project.isProjectLeader(employee));
		assertEquals(project.start,calWeek1);
		
		try {
			sysApp.setTaskEnd(1, 2016, 42);
			Assert.fail();
		} catch (WrongInputException e){}
		
		assertNull(database.getTask(1).end);
		
		try {
			sysApp.setTaskEnd(1, 2016, 44);
		} catch (WrongInputException e){
			Assert.fail();
		}
		assertEquals(database.getTask(1).end.year, 2016);
		assertEquals(database.getTask(1).end.week, 44);
	}
	
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
		Task task = database.getTask(1);
		Project project = database.getProject(task.projectID);
		project.projectLeader=employee;
		assertTrue(project.isProjectLeader(sysApp.currentEmp));
		assertEquals(database.getProject(task.projectID), project);
		
		try {
			sysApp.setTaskEnd(-1, 2016, 44);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		
	}
	
}

