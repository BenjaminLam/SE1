package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import Exceptions_Errors.WrongInputException;
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
			
			
			int taskID=-1;
			
			try {
				taskID = employee.createTask(database, project, taskName);
			} catch (WrongInputException e) {
			}
			
			Task task=super.database.getTask(taskID);
			
			assertNotNull(task);
			assertEquals(task.name,taskName);
			assertEquals(task.projectID,project.ID);
		}
		
		/*
		 *Alternative scenario1: 
		 * employee is not project leader
		 * no task is created
		 * 		this is tested by making sure number of tasks in the database is the same before and after createTask method is called
		 * the system returns an error TODO!
		 */
		
		@Test
		public void testCreateTaskAlt1(){
			int numberOfTasks = database.tasks.size();
			Employee employee=super.database.employees.get(1);
			Project project=super.database.projects.get(0);
			
			//checks employee is not project leader
			assertFalse(project.isProjectLeader(employee));
			
			String taskName="Task01";
			
			try {
				employee.createTask(database, project, taskName);
				Assert.fail();
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.tasks.size(), numberOfTasks);
		}
		
		/*
		 *Alternative scenario1: 
		 * employee is not project leader
		 * no task is created
		 * 		this is tested by making sure number of tasks in the database is the same before and after createTask method is called
		 * the system returns an error TODO!
		 */
		
}
