package project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Exceptions_Enums.WrongInputException;

/*
 * Authors: Martin, Asbj�rn
 */

public class Task {
	public String name;
	public int ID;
	public int projectID;
	public double timeBudget;
	public CalWeek start;
	public CalWeek end;

	public Task(Project project, String name) {
		this.name = name;
		this.projectID = project.ID;
	}

	public boolean inPast() throws WrongInputException {
		if (end == null)
			throw new WrongInputException("Week doesn't excist");
		return end.isBeforeOrWhile(Util.getCurrentWeek());
	}

	protected List<String> getAvailableEmployees (Employee mainEmployee,Database database) throws WrongInputException {	
		Project project=database.getProject(projectID);
		if (!project.isProjectLeader(mainEmployee)) throw new WrongInputException("You are not the project leader for the project of the task");
		
		if (start==null)throw new WrongInputException("Task doesn't have a start date");
		
		if (end==null)throw new WrongInputException("Task doesn't have an end date");
		
		return database.getAvailableEmployees(this.start,this.end);
	}

	protected Task setStart(CalWeek start, Employee employee, Database database) throws WrongInputException {
		CalWeek currentWeek = Util.getCurrentWeek();
		Project project1 = database.getProject(this.projectID);
		
		if (!project1.isProjectLeader(employee)) {
			throw new WrongInputException("You are not projectleader of this project");
		}
		if (project1.start != null) {
			if (start.isBefore(project1.start)) {
				throw new WrongInputException("Selected date is before start date of project");
			}
		}
		if (start.isBefore(currentWeek)) {
			throw new WrongInputException("Invalid date; Date is in the past");
		}
		if (this.end != null) {
			if (start.isAfter(this.end)) {
				throw new WrongInputException("Invalid date; Date is after task end date");
			}
		}
		if (project1.end != null){
			if (start.isAfter(project1.end)){
				throw new WrongInputException("Invalid date; Date is after project end date");
			}
		}
		this.start = start;
		return this;
	}
	
	protected Task setEnd(CalWeek End, Employee employee, Database database) throws WrongInputException {
		CalWeek currentWeek = Util.getCurrentWeek();
		Project project = database.getProject(this.projectID);
		if (!project.isProjectLeader(employee)){
			throw new WrongInputException("You are not the projectleader of this project");
		}
		if(project.start != null){
			if(End.isBefore(project.start)){
				throw new WrongInputException("Invalid date; Date is before project start");
			}
		}
		if(project.end != null){
			if (End.isAfter(project.end)){
				throw new WrongInputException("Invalid date; date is after project end");
			}
		}
		if (End.isBefore(currentWeek)) {
			throw new WrongInputException("Invalid date; Date is in the past");
		}
		if (this.start != null) {
			if (End.isBefore(this.start)) {
				throw new WrongInputException("Invalid date; Date is before start date");
			}
		}
		this.end = End;
		return this;
	}

	protected Task setTimeBudget (double timeBudget) throws WrongInputException{
		if (timeBudget<0) throw new WrongInputException("Timebudget is less than zero. Thats not possible. Please enter a positive timebudget");
		this.timeBudget=timeBudget;
		return this;
	}

	protected void rename (Database database, Employee employee, String name) throws WrongInputException {
		Project project=database.getProject(this.projectID);
		if (!project.projectLeader.equals(employee)) throw new WrongInputException ("You are not the project leader of this project");
		this.name=name;
	}

	public double hoursSpent(Database database) {
		double hoursSpent=0;
		
		for (Assignment assignment:database.assignments) {
			if (assignment.taskID==this.ID) hoursSpent+=assignment.getCumulativeTimeRegisters();
		}

		return hoursSpent;
	}

}
