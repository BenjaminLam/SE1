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
 * Tests for user case project report
 * @author Martin 
 * added alternative scenario 1
 * old alternative scenario 1 is now alternative scenario 2
 * added alternative scenario 3
 */

public class ProjectReportTest extends SampleDataSetupTest {
	/*
	 *Main scenario: 
	 * project exists
	 * employee is project leader
	 * employee creates report
	 */
	
	@Test
	public void projectReportTestMain () {
		Project project=database.projects.get(1);
		assertNotNull(project);
		int projectID = project.ID;
		
		Employee employee=database.employees.get(0);
		assertTrue(project.isProjectLeader(employee));
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.createProjectReport(projectID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(projectReport);	
	}
	
	/*
	 *Alternative scenario1: 
	 * project doesn't exist
	 * employee tries to create report
	 * exception is thrown
	 * report is not created
	 */
	
	@Test
	public void projectReportTestAlt1 () {
		Project project=null;
		assertNull(project);
		int projectID = project.ID;
		
		Employee employee=database.employees.get(0);
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.createProjectReport(projectID);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(projectReport);	
	}
	
	/*
	 *Alternative scenario2: 
	 * project exists
	 * employee is not project leader
	 * employee tries to create report
	 * exception is thrown
	 * report is not created
	 */
	
	@Test
	public void projectReportTestAlt2() {
		Project project=database.projects.get(1);
		assertNotNull(project);
		int projectID = project.ID;
		
		Employee employee=database.employees.get(1);
		assertFalse(project.isProjectLeader(employee));
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.createProjectReport(projectID);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(projectReport);	
	}
	
	/*
	 *Alternative scenario3: 
	 * project exists
	 * employee is project leader
	 * Existing task is created for project
	 * task is done
	 * employee creates report
	 */
	
	@Test
	public void projectReportTestAlt3() throws WrongInputException {
		Project project=database.projects.get(1);
		assertNotNull(project);
		int projectID = project.ID;
		
		Task testTask=new Task (project,"testTask");
		assertNotNull(testTask);
		
		testTask.end=new CalWeek(2000,1);
		try {
			assertTrue(testTask.inPast());
		} catch (WrongInputException e1) {
			Assert.fail();
		}
		
		database.addTask(testTask);
		
		Employee employee=database.employees.get(0);
		assertTrue(project.isProjectLeader(employee));
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.createProjectReport(projectID);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(projectReport);
	}
	
	
}
