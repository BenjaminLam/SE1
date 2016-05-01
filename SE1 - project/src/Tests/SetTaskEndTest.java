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
		CalWeek calWeek1 = new CalWeek(2000,5);
		//Checks if Employee is projectleader
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskEnd(calWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertNotNull(task1.end);
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
		
		assertNull(task1.end);
		try {
			task1.setTaskEnd(calWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertNotNull(task1.end.year);
		assertNotNull(task1.end.week);
		try {
			task1.setTaskEnd(wrongWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertEquals(task1.end, calWeek1);
		}
	}

