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
			
			Task task1 = super.database.getTask(0);
			
			assertFalse(employee.isAssigned(database, task1, employee)); //Skal laves
			
			try {
				assertTrue(employee.createAssignment(database, task1, employee)); //skal laves
			} catch (WrongInputException e) {
			}
			
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments); //Ved ikke om vi skal indfører et ID for dette?
			
			Assignment assignment = super.database.assignments.get(numberOfAssignments+1);
			
			assertNotNull(assignment); 
			assertEquals(assignment.taskID,task1);
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
		public void testCreateAssignmentAlt1(){
			int numberOfAssignments = database.getNumberOfAssignments(); 
			Employee employee=super.database.employees.get(1);
			Project project=super.database.projects.get(0);
			
			//checks employee is not project leader
			assertFalse(project.isProjectLeader(employee));
			
			Task task1 = super.database.getTask(0);
			
			try {
				employee.createAssignment(database, task1, employee);
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
		public void testCreateAssignmentAlt2(){
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=super.database.employees.get(0);
			Project project=super.database.projects.get(0);
			
			assertTrue(project.isProjectLeader(employee));
			
			Task task1 = super.database.getTask(0);
			
			assertTrue(employee.isAssigned(database, task1, employee));
			
			try {
				employee.createAssignment(database, task1, employee);
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
		public void testCreateAssignmentAlt3(){
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=super.database.employees.get(0);
			
			Task task1 = super.database.getTask(0);
			task1=null;
			
			try {
				employee.createAssignment(database, task1, employee);
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
		public void testCreateAssignmentAlt4(){
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee employee=null;
			
			Task task1 = super.database.getTask(0);
			
			try {
				employee.createAssignment(database, task1, employee);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) { 
			}
			
			//checks createTask hasn't created task; 
			assertEquals(database.getNumberOfAssignments(), numberOfAssignments);
		}
		
		
		
		
}



