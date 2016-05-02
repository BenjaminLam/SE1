package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Errors.*;
import project.*;
/* Tests for setTaskend
 * Author Asbjørn
 */
public class SetTaskEndTest extends SampleDataSetup0 {
	
	@Test
	public void setTaskEndMain(){
		//Initialises objects
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016, 20);
		//Checks if Employee is projectleader
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskEnd(calWeek1, employee);
		} catch (WrongInputException e) {
			Assert.fail ();
		}
		assertEquals(task1.end.year, calWeek1.year);
		assertEquals(task1.end.week, calWeek1.week);
	}
	/*
	 * Alternative scenario 1
	 * The selected date is before current date
	 */
	@Test
	public void setTaskEndAlt1() {
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek wrongWeek1 = new CalWeek(2015,32);
		assertTrue(project.isProjectLeader(employee));

		try {
			task1.setTaskEnd(calWeek1, employee);
		} catch (WrongInputException e) {
			Assert.fail();
		}
		assertNotNull(task1.end.year);
		assertNotNull(task1.end.week);
		try {
			task1.setTaskEnd(wrongWeek1, employee);
			Assert.fail();
		} catch (WrongInputException e) {
		}
		assertEquals(task1.end, calWeek1);
		}
	/*
	 * Alternative scenario 3
	 * 
	 */
	@Test
	public void setTaskEndAlt2(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek calWeek2 = new CalWeek(2016,32);
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskStart(calWeek1, employee, super.database);		
		} catch (WrongInputException e) { 
			Assert.fail();
		}	
		try {
			task1.setTaskEnd(calWeek2, employee);
			Assert.fail();
		} catch (WrongInputException e){};
		assertNull(task1.end);
	}
	}

