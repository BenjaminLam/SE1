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
 * Tests for user case createAssignment
 * @author Christian 
 */

public class CreateAssignmentTest extends SampleDataSetupTest {
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
		public void testCreateAssignmentMain () throws WrongInputException {
			int numberOfAssignments = sysApp.getNumberOfAssignments();
			Employee leader=super.database.employees.get(0);
			Employee worker=super.database.employees.get(1);
			Project project=super.database.projects.get(1);
			
			sysApp.logIn(leader.ID);
			
			assertTrue(project.isProjectLeader(leader));
			
			Task task1 = super.database.getTask(2);
			
			assertFalse(leader.isAssigned(database, task1, worker)); 
			
			try {
				sysApp.manTask(task1.ID, worker.ID); 
			} catch (WrongInputException e) {
			}
			
			assertEquals(sysApp.getNumberOfAssignments(), numberOfAssignments); 
			
			Assignment assignment = super.database.assignments.get(numberOfAssignments+1);
			
			assertNotNull(assignment); 
			assertEquals(assignment.taskID,task1);
			assertEquals(assignment.employeeID,worker.ID); 
		}
		
		/*
		 *Alternative scenario1: 
		 * employee is not project leader
		 * employee tries to create task for project
		 * exception is thrown
		 * no task has been created
		 */
		
		@Test
		public void testCreateAssignmentAlt1() throws WrongInputException{
			int numberOfAssignments = sysApp.getNumberOfAssignments(); 
			Employee worker=super.database.employees.get(1);
			Project project=super.database.projects.get(1);
			
			sysApp.logIn(worker.ID);
			
			//checks employee is not project leader
			assertFalse(project.isProjectLeader(worker));
			
			Task task1 = super.database.getTask(0);
			
			try {
				sysApp.manTask(task1.ID, worker.ID);
				Assert.fail(); //checks exception is thrown
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created assignment; 
			assertEquals(sysApp.getNumberOfAssignments(), numberOfAssignments);
		}

		/*
		 *Alternative scenario2: 
		 * employee tries to create a assignment
		 * but the task and employee is already assigned
		 * exception is thrown
		 * no task has been created
		 */
		
		@Test
		public void testCreateAssignmentAlt2() throws WrongInputException{
			int numberOfAssignments = database.getNumberOfAssignments();
			Employee leader=super.database.employees.get(0);
			Employee worker=super.database.employees.get(1);
			Project project=super.database.projects.get(1);
			
			sysApp.logIn(leader.ID);
			
			assertTrue(project.isProjectLeader(leader));
			
			Task task1 = super.database.getTask(0);
			
			assertTrue(leader.isAssigned(database, task1, worker));
			
			try {
				sysApp.manTask(task1.ID, worker.ID);
				Assert.fail(); //checks exception is thrown
			} catch (WrongInputException e) {
			}
			
			//checks createTask hasn't created task; 
			assertEquals(sysApp.getNumberOfAssignments(), numberOfAssignments);
			
		}
		
		/*
		 *Alternative scenario3:
		 * employee tries to create assignment for non excisting task (null)
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateAssignmentAlt3() throws WrongInputException{
			int numberOfAssignments = sysApp.getNumberOfAssignments();
			Employee leader=super.database.employees.get(0);
			Employee worker=super.database.employees.get(1);
			
			sysApp.logIn(leader.ID);
			
			Task task1 = super.database.getTask(0);
			task1=null;
			
			try {
				sysApp.manTask(task1.ID, worker.ID);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) { 
			}
			
			//checks createTask hasn't created task; 
			assertEquals(sysApp.getNumberOfAssignments(), numberOfAssignments);
		}
		
		/*
		 *Alternative scenario4: 
		 * employee tries to create assignment for non existing employee (null)
		 * method throws exception
		 * no task has been added
		 */
		
		@Test
		public void testCreateAssignmentAlt4(){
			int numberOfAssignments = sysApp.getNumberOfAssignments();
			Employee employee=null;
			
			Task task1 = super.database.getTask(0);
			
			try {
				sysApp.manTask(task1.ID, employee.ID);
				Assert.fail(); //method throws exception
			} catch (WrongInputException e) { 
			}
			
			//checks createTask hasn't created task; 
			assertEquals(sysApp.getNumberOfAssignments(), numberOfAssignments);
		}
		
		
		
		
}



