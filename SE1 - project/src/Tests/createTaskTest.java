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

public class createTaskTest extends SampleDataSetupTest {
		/*
		 *Main scenario: 
		 * employee is project leader
		 * employee enters name of task to create
		 * system creates task with name
		 * 		check task exists
		 * 		check name of task
		 * 		check task is under right project
		 */
		
		@Test
		public void testCreateTaskMain () {
			Employee employee=super.database.employees.get(0);
			Project project=super.database.projects.get(1);
			
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
		 * employee tries to create task for project
		 * exception is thrown
		 * no task has been created
		 */
		
		@Test
		public void testCreateTaskAlt1(){
			int numberOfTasks = sysApp.getNumberOfTasks();
			Employee employee=super.database.employees.get(1);
			Project project=super.database.projects.get(1);
			
			//checks employee is not project leader
			assertFalse(project.isProjectLeader(employee));
			
			String taskName="Task01";
			
			try {
				employee.createTask(database, project, taskName);
				Assert.fail(); //checks exception is thrown
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(sysApp.getNumberOfTasks(), numberOfTasks);
		}
		
		/*
		 *Alternative scenario2: 
		 * employee tries to create task for non excisting project (null)
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateTaskAlt2(){
			int numberOfTasks = sysApp.getNumberOfTasks();
			Employee employee=super.database.employees.get(0);
			Project project=null;
			
			String taskName="Task01";
			
			try {
				employee.createTask(database, project, taskName);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(sysApp.getNumberOfTasks(), numberOfTasks);
		}
		
		/*
		 *Alternative scenario3: 
		 * employee is project leader
		 * *employee creates task with name already excisting under another project
		 * *employee creates task with arbitrary name for project which already contains task 
		 * employee tries to create task with name already existing under selected project
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateTaskAlt3(){
			Employee employee=super.database.employees.get(0);
			Project project=super.database.projects.get(1);
			
			assertTrue(project.isProjectLeader(employee));
			
			//add task to secondary project
			super.database.addTask(new Task(super.database.projects.get(1),"Task"));
			
			//adding task with same name^ to main project
			try {
				employee.createTask(database, project, "Task");
			} catch (WrongInputException e) {
				Assert.fail();
			}
			
			//adding task with new name to main project
			try {
				employee.createTask(database, project, "Task01");
			} catch (WrongInputException e) {
				Assert.fail();
			}
			
			//adding task with already excisting name in project
			int numberOfTasks = sysApp.getNumberOfTasks();
			try {
				employee.createTask(database, project, "Task01");
				Assert.fail();
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(sysApp.getNumberOfTasks(), numberOfTasks);
		}
		
		
		
		
}
