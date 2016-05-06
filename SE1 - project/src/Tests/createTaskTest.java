package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Enums.*;
import project.*;


/**
 * Tests for user case createTask
 * @author all group members 
 * added two more alternative scenarios under alternate test 3
 * new alternative scenarios are marked with *
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
			Employee employee=database.getEmployee(0);
			Project project=database.getProject(1);
			
			assertTrue(project.isProjectLeader(employee));
			
			String taskName="Task01";
			
			Task task = new Task(project, taskName);
			try {
				task = employee.createTask(database, project.ID, taskName);
			} catch (WrongInputException e) {
			}
			
			
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
			int numberOfTasks = database.numberOfTasks();
			Employee employee=super.database.employees.get(1);
			Project project=super.database.projects.get(1);
			
			//checks employee is not project leader
			assertFalse(project.isProjectLeader(employee));
			
			String taskName="Task01";
			
			try {
				employee.createTask(database, project.ID, taskName);
				Assert.fail(); //checks exception is thrown
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.numberOfTasks(), numberOfTasks);
		}
		
		/*
		 *Alternative scenario2: 
		 * employee tries to create task for non existing project (null)
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateTaskAlt2(){
			int numberOfTasks = database.numberOfTasks();
			Employee employee=super.database.employees.get(0);
			Project project=null;
			
			String taskName="Task01";
			
			try {
				employee.createTask(database, project.ID, taskName);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.numberOfTasks(), numberOfTasks);
		}
		
		/*
		 *Alternative scenario3: 
		 * employee is project leader
		 * *employee creates task with name already existing under another project
		 * *employee creates task with arbitrary name for project which already contains task 
		 * employee tries to create task with name already existing under selected project
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateTaskAlt3() throws WrongInputException{
			Employee employee=super.database.employees.get(0);
			Project project=super.database.projects.get(1);
			
			assertTrue(project.isProjectLeader(employee));
			
			//add task to secondary project
			database.addTask(new Task(super.database.projects.get(1),"Task"));
			
			//adding task with same name^ to main project
			try {
				employee.createTask(database, project.ID, "Task");
			} catch (WrongInputException e) {
				Assert.fail();
			}
			
			//adding task with new name to main project
			try {
				employee.createTask(database, project.ID, "Task01");
			} catch (WrongInputException e) {
				Assert.fail();
			}
			
			//adding task with already existing name in project
			int numberOfTasks = database.numberOfTasks();
			try {
				employee.createTask(database, project.ID, "Task01");
				Assert.fail();
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.numberOfTasks(), numberOfTasks);
		}
		
		
		
		
}
