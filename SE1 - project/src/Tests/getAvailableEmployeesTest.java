package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
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

public class getAvailableEmployeesTest extends SampleDataSetup0 {
	/* 
	 * main scenario:
	 * 
	 * Excisting Task is selected
	 * Employee is project leader for project task is connected to
	 * System returns information
	 * 
	 * Test information is correct??
	 */
	
	@Test
	public void getAvailableEmployeesMain() {
		Employee employee=super.database.employees.get(0);
		Task task=super.database.getTask(0);
		
		assertNotNull(task);
		
		int projectID=task.projectID;

		Project project=super.database.getProject(projectID);
		
		assertTrue(project.isProjectLeader(employee));
		
	
		//super.database.getAvailableEmployees(employee, task);
	}
	
	
	
}
