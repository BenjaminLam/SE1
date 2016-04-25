package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import project.*;


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
			Project project=super.database.projects.get(0);
			
			assertTrue(project.isProjectLeader(employee));
			
			String taskName="Task01";
			
			int taskID=employee.createTask(database, project.ID, taskName);
			
			Task task=super.database.getTask(taskID);
			
			assertNotNull(task);
			assertEquals(task.name,taskName);
			assertEquals(task.projectID,project.ID);
		}
}
