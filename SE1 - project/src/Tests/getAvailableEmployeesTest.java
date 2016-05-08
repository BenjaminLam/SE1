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
 * Tests for user case get available employees
 * @author Martin
 * added two more alternative scenarios:
 * alternative3 and alternative 4
 */

//Test information is correct from returned list??

public class getAvailableEmployeesTest extends SampleDataSetupTest {
	/* 
	 * main scenario:
	 * 
	 * Existing Task is selected
	 * task has start and end
	 * Employee is project leader for project task is connected to
	 * System returns information
	 * 
	 *
	 */
	@Test
	public void getAvailableEmployeesMain() throws WrongInputException {
		Employee employee=database.employees.get(0);
		Task task=database.getTask(0);
		
		assertNotNull(task);
		
		task.start=new CalWeek(2000,1);
		task.end=new CalWeek(2000,4);
		
		assertNotNull(task.start);
		assertNotNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		project.projectLeader=employee;
		
		assertTrue(project.isProjectLeader(employee));
		
		List<String> employeesAvailable=null;
		employeesAvailable = database.getAvailableEmployees(task.start, task.end);
	
		assertNotNull(employeesAvailable);	
	}
	
	/*  
	 * alternative  scenario 1:
	 * 
	 * Existing Task is selected
	 * Task has start and end
	 * Employee is not project leader for project task is connected to
	 * exception is thrown
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt1() throws WrongInputException {
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
		
		
		String[] employeesAvailable=null;
		
		try {
			employeesAvailable=sysApp.employeesForTask(task.ID);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
		
		assertNull(employeesAvailable);	
	}
	
	/* 
	 * alternative  scenario 2:
	 * 
	 * non existing Task is selected
	 * exception is thrown
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt2() throws WrongInputException {
		Employee employee=database.employees.get(0);
		int taskID = -1;
		
		Task task = database.getTask(taskID);
		assertNull(task);
			
		String[] employeesAvailable=null;
		try {
			employeesAvailable=sysApp.employeesForTask(taskID);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
	}
	
	/* 
	 * alternative scenario 3:
	 * 
	 * Existing Task is selected
	 * Task has start but not end
	 * Employee is project leader for project task is connected to
	 * System throws exception
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt3() throws WrongInputException {
		Employee employee=database.employees.get(0);
		Task task=database.getTask(1);
		
		assertNotNull(task);
		
		task.start=new CalWeek(2000,1);
		
		assertNotNull(task.start);
		assertNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		project.projectLeader=employee;
		
		assertTrue(project.isProjectLeader(employee));
		
		String [] employeesAvailable=null;
		try {
			employeesAvailable=sysApp.employeesForTask(task.ID);
			Assert.fail();
		} catch (WrongInputException e) {
			
		}
	
	}

	/* 
	 * alternative scenario 4:
	 * 
	 * Existing Task is selected
	 * Task has end but no start
	 * Employee is project leader for project task is connected to
	 * System throws exception
	 * 
	 */
	@Test
	public void getAvailableEmployeesAlt4() throws WrongInputException {
		Employee employee=database.employees.get(0);
		Task task=database.getTask(0);
		
		assertNotNull(task);
		
		task.end=new CalWeek(2000,4);
		
		assertNull(task.start);
		assertNotNull(task.end);
		
		int projectID=task.projectID;

		Project project=database.getProject(projectID);
		
		project.projectLeader=employee;
		
		assertTrue(project.isProjectLeader(employee));
		
		String [] employeesAvailable=null;
		try {
			employeesAvailable=sysApp.employeesForTask(task.ID);
			Assert.fail();
		} catch (WrongInputException e) {
	
		assertNull(employeesAvailable);	
	}
	}
}
