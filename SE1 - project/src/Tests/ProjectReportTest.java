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
	 * project excists
	 * employee is project leader
	 * employee creates report
	 */
	
	@Test
	public void projectReportTestMain () {
		Project project=database.projects.get(1);
		assertNotNull(project);
		
		Employee employee=database.employees.get(0);
		assertTrue(project.isProjectLeader(employee));
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.getProjectReport(project, employee);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(projectReport);	
	}
	
	/*
	 *Alternative scenario1: 
	 * project doesn't excist
	 * employee tries to create report
	 * exception is thrown
	 * report is not created
	 */
	
	@Test
	public void projectReportTestAlt1 () {
		Project project=null;
		assertNull(project);
		
		Employee employee=database.employees.get(0);
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.getProjectReport(project, employee);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(projectReport);	
	}
	
	/*
	 *Alternative scenario2: 
	 * project excists
	 * employee is not project leader
	 * employee tries to create report
	 * exception is thrown
	 * report is not created
	 */
	
	@Test
	public void projectReportTestAlt2() {
		Project project=database.projects.get(1);
		assertNotNull(project);
		
		Employee employee=database.employees.get(1);
		assertFalse(project.isProjectLeader(employee));
		
		List<String> projectReport=null;
		
		try {
			projectReport=sysApp.getProjectReport(project, employee);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertNull(projectReport);	
	}
	
	/*
	 *Alternative scenario3: 
	 * project excists
	 * employee is project leader
	 * excisting task is created for project
	 * task is done
	 * employee creates report
	 */
	
	@Test
	public void projectReportTestAlt3() {
		Project project=database.projects.get(1);
		assertNotNull(project);
		
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
			projectReport=sysApp.getProjectReport(project, employee);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(projectReport);
	}
	
	
}
