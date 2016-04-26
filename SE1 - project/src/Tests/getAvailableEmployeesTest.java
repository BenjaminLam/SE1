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


/**
 * Tests for user case createTask
 * @author all group members 
 * added two more alternative scenarios under alternate test 3
 * new alternative scenarions are marked with *
 */

//Test information is correct from returned list??

public class getAvailableEmployeesTest extends SampleDataSetup0 {
	/* 
	 * main scenario:
	 * 
	 * Excisting Task is selected
	 * task has start and end
	 * Employee is project leader for project task is connected to
	 * System returns information
	 * 
	 *
	 */
	@Test
	public void getAvailableEmployeesMain() {
		Employee employee=database.employees.get(0);
		Task task=database.getTask(0);
		
		assertNotNull(task);
		
		task.start=new CalWeek(2000,1);
		task.end=new CalWeek(2000,4);
		
		assertNotNull(task.start);
		assertNotNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		assertTrue(project.isProjectLeader(employee));
		
		List<String> employeesAvailable=null;
		try {
			employeesAvailable = database.getAvailableEmployees(employee, task);
		} catch (WrongInputException e) {
			Assert.fail();
		}
	
		assertNotNull(employeesAvailable);	
	}
	
	/*  
	 * alternative  scenario 1:
	 * 
	 * Excisting Task is selected
	 * Task has start and end
	 * Employee is not project leader for project task is connected to
	 * exception is thrown
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt1() {
		Employee employee=database.employees.get(1);
		Task task=database.getTask(0);
		
		assertNotNull(task);
		
		task.start=new CalWeek(2000,1);
		task.end=new CalWeek(2000,4);
		
		assertNotNull(task.start);
		assertNotNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		assertFalse(project.isProjectLeader(employee));
		
		
		List<String> employeesAvailable=null;
		try {
			employeesAvailable=database.getAvailableEmployees(employee, task);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
	
		assertNull(employeesAvailable);	
	}
	
	/* 
	 * alternative  scenario 2:
	 * 
	 * non excisting Task is selected
	 * exception is thrown
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt2() {
		Employee employee=database.employees.get(0);
		Task task=null;
		
		assertNull(task);
			
		List<String> employeesAvailable=null;
		try {
			employeesAvailable=database.getAvailableEmployees(employee, task);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
	
		assertNull(employeesAvailable);	
	}
	
	/* 
	 * alternative scenario 3:
	 * 
	 * Excisting Task is selected
	 * Task has start but not end
	 * Employee is project leader for project task is connected to
	 * System throws exception
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt3() {
		Employee employee=database.employees.get(0);
		Task task=database.getTask(0);
		
		assertNotNull(task);
		
		task.start=new CalWeek(2000,1);
		
		assertNotNull(task.start);
		assertNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		assertTrue(project.isProjectLeader(employee));
		
		List<String> employeesAvailable=null;
		try {
			employeesAvailable = database.getAvailableEmployees(employee, task);
			Assert.fail();
		} catch (WrongInputException e) {
		}
	
		assertNull(employeesAvailable);	
	}

	/* 
	 * alternative scenario 4:
	 * 
	 * Excisting Task is selected
	 * Task has end but no start
	 * Employee is project leader for project task is connected to
	 * System throws exception
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt4() {
		Employee employee=database.employees.get(0);
		Task task=database.getTask(0);
		
		assertNotNull(task);
		
		task.end=new CalWeek(2000,4);
		
		assertNull(task.start);
		assertNotNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		assertTrue(project.isProjectLeader(employee));
		
		List<String> employeesAvailable=null;
		try {
			employeesAvailable = database.getAvailableEmployees(employee, task);
			Assert.fail();
		} catch (WrongInputException e) {
		}
	
		assertNull(employeesAvailable);	
	}
}
