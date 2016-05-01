package Tests;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import Exceptions_Errors.*;
import project.*;
/* Tests for setTaskStart
 * Author Asbjørn
 */
public class SetTaskStartTest extends SampleDataSetup0 {
	/*
	 * Main scenario
	 * Projectleader tries to change the start date of a task.
	 * The Start date is changed.
	 */
	@Test
	public void setTaskStartMain(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		assertNotNull(calWeek1.week);
		assertNotNull(calWeek1.year);
		assertTrue(project.isProjectLeader(employee));
		
		try {
			task1.setTaskStart(calWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertEquals(task1.start.week,calWeek1.week);
		assertEquals(task1.start.year,calWeek1.year);
	}
	/* 
	 * Alternative scenario 2
	 * The selected date is in the past
	 * Method returns error
	 */
	@Test
	public void setTaskStartAlt2(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek wrongWeek1 = new CalWeek(2015,32);
		
		assertTrue(project.isProjectLeader(employee));
		
		assertNull(task1.start);
		try {
			task1.setTaskStart(calWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertNotNull(task1.start.year);
		assertNotNull(task1.start.week);
		try {
			task1.setTaskStart(wrongWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertEquals(task1.start, calWeek1);
	}
	/*
	 * Alternative scenario 4
	 * The selected date is after task end date
	 */
	@Test
	public void setTaskStartAlt3(){
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2016,43);
		CalWeek wrongWeek1 = new CalWeek(2016,44);
		
		assertTrue(project.isProjectLeader(employee));
		
		task1.setTaskEnd(calWeek1, employee);
		try {
			task1.setTaskStart(wrongWeek1, employee);
		} catch (WrongInputException e) {
		}
		assertEquals(task1.end.year,calWeek1.year);
		assertEquals(task1.end.week,calWeek1.week);
	}
}
