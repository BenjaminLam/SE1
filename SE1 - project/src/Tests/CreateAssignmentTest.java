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
 * Tests for user case createAssignment
 * @author Christian 
 */

public class CreateAssignmentTest extends SampleDataSetup0 {
		/*
		 *Main scenario: 
		 * employee is project leader
		 * employee enters taskID and EmployeeID
		 * system creates an assignment
		 * 		check assignment exists
		 * 		check if its the right employee
		 * 		check if its the right task
		 */
		
		@Test
		public void testCreateAssignmentMain () {
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=super.database.employees.get(0);
			Project project=super.database.projects.get(0);
			
			assertTrue(project.isProjectLeader(employee));
			
			String taskName="Task01";
			int taskID=1;
			
			taskID = employee.createTask(database, project, taskName); //Jeg vil gerne have det her udenfor, ellers kan den sagtens kaldes i hver test
			
			assertFalse(employee.isAssigned(taskID)); //Skal laves
			
			try {
				assignmentID = employee.createAssignment(database, taskName, employee); //skal laves
			} catch (WrongInputException e) {
			}
			
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments); //Ved ikke om vi skal indfører et ID for dette?
			
			Assignment assignment = super.database.assignments.get(numberOfAssignments+1);
			
			assertNotNull(assignment); 
			assertEquals(assignment.taskID,taskID);
			assertEquals(assignment.employeeID,employee.ID); 
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
			int numberOfAssignments = database.getNumberOfAssignments(); //Kommer vi til at indfører antal?
			Employee employee=super.database.employees.get(1);
			Project project=super.database.projects.get(0);
			
			//checks employee is not project leader
			assertFalse(project.isProjectLeader(employee));
			
			String taskName="Task01";
			employee.createTask(database, project, taskName);
			
			try {
				assignmentID = employee.createAssignment(database, taskName, employee);
				Assert.fail(); //checks exception is thrown
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created assignment; 
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments);
		}

		/*
		 *Alternative scenario2: 
		 * employee tries to create a assignment
		 * but the task and employee is already assigned
		 * exception is thrown
		 * no task has been created
		 */
		
		@Test
		public void testCreateTaskAlt2(){
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=super.database.employees.get(0);
			Project project=super.database.projects.get(0);
			
			assertTrue(project.isProjectLeader(employee));
			
			String taskName="Task01";
			int taskID=1;
			
			taskID = employee.createTask(database, project, taskName); 
			
			String assignmentName="Assignment for the task "+taskName+" for the employee "+employee;
			int assignmentID=-1;
			
			assertTrue(employee.isAssigned(taskID));
			
			try {
				assignmentID = employee.createAssignment(database, taskName, employee);
				Assert.fail(); //checks exception is thrown
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments);
			
		}
		
		/*
		 *Alternative scenario3:
		 * employee tries to create assignment for non excisting task (null)
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateTaskAlt3(){
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=null;
			
			
			String taskName=null;
			
			try {
				employee.createAssignment(database, taskName, employee);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) { 
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments);
		}
		
		/*
		 *Alternative scenario4: 
		 * employee tries to create assignment for non excisting employee (null)
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateTaskAlt4(){
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=super.database.employees.get(0);
			
			
			String taskName="Task01";
			
			try {
				employee.createAssignment(database, taskName, employee);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) { 
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments);
		}
		
		
		
		
}



