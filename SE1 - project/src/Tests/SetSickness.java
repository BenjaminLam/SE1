package Tests;

import org.junit.Assert;
import org.junit.Test;

import Exceptions_Enums.WrongInputException;
import project.Employee;

/**
 * Tests for user case setSickness
 * @author Christian 
 */

public class SetSickness extends SampleDataSetupTest {
	
	//Not Complete
	
	/*
	 *Main scenario: 
	 * employee wants to create booking for sickness
	 * employee uses the method setSickness
	 * system creates set employee as sick
	 * 		check if employee already is sick
	 * 		check if employee is booked as sick
	 */
	
	@Test
	public void testSetSicknessMain () {
		Employee employee=super.database.employees.get(0);
		
		assertFalse(sysApp.isSick(employee.ID)); //metoden skal laves
		
		try { //tror heller ikke at dette er nødvendigt
			sickness = sysApp.setSickness(employee.ID); //skal laves
		} catch (WrongInputException e) {
		}
		
		//checks if employee is sick
		assertTrue(employee.isSick(employee.ID));
	}
	
	/*
	 *Alternative scenario1: 
	 * employee is already sick
	 * employee tries to book himself as sick
	 * exception is thrown
	 * no sick assignment has been created
	 */
	
	@Test
	public void testCreateTaskAlt1(){
		Employee employee=super.database.employees.get(1);
		
		assertTrue(employee.isSick(employee.ID));
		
		try { 
			sickness = employee.setSickness(employee.ID);
			Assert.fail(); //checks if exception is thrown
		} catch (WrongInputException e) { //exception skal laves
		}
		
	}
}
