package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import project.Employee;
import project.Task;

/**
 * @TODO.
 * @author m&b / m
 *
 */

public class createTaskTest extends SampleDataSetup0 {
		/*
		 *Main scenario: 
		 * employee is project leader
		 * employee enters project id for project which he is leader of 
		 * employee enters name of task to create
		 * system creates task with name
		 * 		check task exists
		 * 		check name of task
		 * 		check task is under right project
		 */
		
		@Test
		public void testCreateTaskMain () {
			Employee employee=super.database.employees.get(0);
			
			assertTrue(super.database.isProjectLeader(employee));
			
			int numberOfTasks=super.database.tasks.size();
			
			int projectID=0;
			String taskName="Task01";
			
			employee.createTask(database, projectID, taskName);
			
			Task task=super.database.tasks.get(0);
			
			assertNotNull(task);
			assertEquals(task.name,taskName);
			assertEquals(task.projectID,projectID);
		}
}
