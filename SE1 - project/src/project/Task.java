package project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Exceptions_Enums.WrongInputException;

//set task start/task end hvorfor getProject(0) og ikke getProject(task.projectID)

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
		Project project1 = database.getProject(0);
		if (!project1.isProjectLeader(employee)) {
			throw new WrongInputException("You are not projectleader of this project");
		}
		if (project1.start != null) {
			if (start.isBeforeOrWhile(project1.start)) {
				throw new WrongInputException("Selected date is before start date of project");
			}
		}
		if (start.isBeforeOrWhile(currentWeek)) {
			throw new WrongInputException("Invalid date; Date is in the past");
		}
		if (this.end != null) {
			if (start.isAfterOrWhile(this.end)) {
				throw new WrongInputException("Invalid date; Date is after task end date");
			}
		}
		if (project1.end != null){
			if (start.isAfterOrWhile(project1.end)){
				throw new WrongInputException("Invalid date; Date is after project end date");
			}
		}
		this.start = start;
		return this;
	}
	
	protected Task setEnd(CalWeek End, Employee employee, Database database) throws WrongInputException {
		CalWeek currentWeek = Util.getCurrentWeek();
		Project project = database.getProject(0);
		if (!project.isProjectLeader(employee)){
			throw new WrongInputException("You are not the projectleader of this project");
		}
		if(project.start != null){
			if(End.isBeforeOrWhile(project.start)){
				throw new WrongInputException("Invalid date; Date is before project start");
			}
		}
		if(project.end != null){
			if (End.isAfterOrWhile(project.end)){
				throw new WrongInputException("Invalid date; date is after project end");
			}
		}
		if (End.isBeforeOrWhile(currentWeek)) {
			throw new WrongInputException("Invalid date; Date is in the past");
		}
		if (this.start != null) {
			if (End.isBeforeOrWhile(this.start)) {
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
	
}
