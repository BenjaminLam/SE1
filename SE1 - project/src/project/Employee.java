package project;

import java.util.ArrayList;
import java.util.List;

import Exceptions_Enums.*;

/*
 * Authors: Benjamin, Christian, Asbj�rn, Martin
 * 
 */

public class Employee {
	public String name;
	public int ID;
	
	public Employee (String name){
		this.name=name;
	}
	
	public boolean equals (Employee employee) {
		if (employee==null) return false;
		if (this.ID==employee.ID) return true;
		return false;
	}

	protected boolean isProjectLeader(Database database) {
		return database.isProjectLeader(this);
	}
	
	public boolean isAvailable(WorkPeriod period,Database database ){
		for(Object object: dayBookings(period.day, database).mainInfo){
			WorkPeriod booking=(WorkPeriod) object;
			
			if(booking.overlapse(period)){
				return false;
			}
		}
		return true;
	}
	
	public MyMap dayBookings(CalDay day, Database database){
		return database.getEmployeeDayBookings (this,day);
	}
	
	public Task createTask (Database database, int projectID, String name) throws WrongInputException {
		Project project=database.getProject(projectID);
		
		if (project==null) { 
			throw new WrongInputException("Project doesn't exist");	
		}
		if(!project.isProjectLeader(this)) {
			throw new WrongInputException("You are not the project leader of the selected project");
		}
		if (database.taskExists(project, name)) {
			throw new WrongInputException("The task already exists in this project");
		}
		Task task= new Task (project, name);
		return database.addTask(task);
	}

	protected Assignment manTask(Database database, int taskID, int employeeID) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException("No task exists with that taskID");
		
		Employee employee=database.getEmployee(employeeID);
		if (employee==null) throw new WrongInputException("No employee exists with that employeeID");
		
		if (employee.isAssigned(database, task, employee)) throw new WrongInputException ("An assignment for this task and employee already exists");
		
		Project project=database.getProject(task.projectID);
		if (!project.isProjectLeader(this)) throw new WrongInputException("You are not projectleader of this project, therefore you can not man this task.");
		return database.createAssignment(employee,task);
	}
	
	protected double hoursAvailable (Database database, CalWeek start, CalWeek end) {
		double availableTime=start.weeksBetween(end)*37.5;
		
		return availableTime-hoursBooked(database, start,end);
	}
	
	protected Task setTaskBudgetTime (Database database, Task task, double timeBudget) throws WrongInputException {
		if (task==null) throw new WrongInputException("Task doesn't exist.");
		Project project=database.getProject(task.projectID);
		if(!project.isProjectLeader(this)) throw new WrongInputException("You are not projectleader for this project. You can therefore not create an assignment.");
		return task.setTimeBudget(timeBudget);
	}
	
	protected WorkPeriod createBooking (Database database, Task task, CalDay calDay, double start, double end) throws WrongInputException {
		WorkPeriod wp=new WorkPeriod(calDay, start, end);
		Assignment assignment=database.getAssignment(task.ID, this.ID);
		if (assignment==null) throw new WrongInputException ("Employee is not working at selected task");
		assignment.addBooking(wp);
		return wp;
	}
		
	public boolean isAssigned (Database database, Task task, Employee employee){
		if(database.getAssignment(task.ID , employee.ID)== null){
			return false;
		}
		return true;
	}
	
	protected void setSickness (Database database) throws WrongInputException{
		if(isSick(database,this)) throw new WrongInputException("You are already registered as sick today.");
		WorkPeriod wp = new WorkPeriod(Util.getCurrentDay(),9.0,16.5);
		database.getAssignment(0, this.ID).addBooking(wp);
	}	
	
	public boolean isSick(Database database, Employee employee) throws WrongInputException{
		int taskID = 0;
		int employeeID = employee.ID;
		WorkPeriod wp = new WorkPeriod(Util.getCurrentDay(),9.0,16.5);
		for (Assignment ass:database.assignments) {
			if(employeeID == ass.employeeID && ass.taskID==taskID){
			for(WorkPeriod booking:ass.bookings)	
				if(booking.equals(wp)){
					return true;
					}
				}
			}
		return false;
	}
	
	public void setVacation(Database database, CalDay start, CalDay end) throws WrongInputException{
		List<CalDay> daysBetween = start.getDaysBetween(end);
		
		if (end.week.isBefore(start.week)) throw new WrongInputException ("End of vacation can't be before start");
		if (end.week.equals(start.week) && end.day<=start.day) throw new WrongInputException ("Start of vacation must be after end of vacation");
		
		for(CalDay day: daysBetween){
			WorkPeriod wp = new WorkPeriod(day,9.0,16.5);
			if(isOnVacation(database,this,day)) throw new WrongInputException("You are already registered as on vacation.");
			database.getAssignment(1, this.ID).addBooking(wp);
		}
	}
	
	public boolean isOnVacation(Database database, Employee employee,CalDay day) throws WrongInputException{
		int taskID = 1;
		int employeeID = employee.ID;
		WorkPeriod wp = new WorkPeriod(day,9.0,16.5);
		for (Assignment ass:database.assignments) {
			if(employeeID == ass.employeeID && ass.taskID==taskID){
			for(WorkPeriod booking:ass.bookings)	
				if(booking.equals(wp)){
					return true;
					}
				}
			}
		return false;
	}
	
	public void setCourse(Database database,CalDay start, CalDay end) throws WrongInputException{
		List<CalDay> daysBetween = start.getDaysBetween(end);
		
		if (end.week.isBefore(start.week)) throw new WrongInputException ("End of course can't be before start");
		if (end.week.equals(start.week) && end.day<=start.day) throw new WrongInputException ("Start of course must be after end of vacation");
		
		
		for(CalDay day: daysBetween){
			WorkPeriod wp = new WorkPeriod(day,9.0,16.5);
			if(isOnCourse(database,this,day)) throw new WrongInputException("You are already registered as on course.");
			database.getAssignment(2, this.ID).addBooking(wp);
		}
	}
	
	public boolean isOnCourse(Database database, Employee employee,CalDay day) throws WrongInputException{
		int taskID = 1;
		int employeeID = employee.ID;
		WorkPeriod wp = new WorkPeriod(day,9.0,16.5);
		for (Assignment ass:database.assignments) {
			if(employeeID == ass.employeeID && ass.taskID==taskID){
			for(WorkPeriod booking:ass.bookings)	
				if(booking.equals(wp)){
					return true;
					}
				}
			}
		return false;
	}

	public double hoursRegisteredToday (Database database) throws WrongInputException {
		List<Assignment> assignments=database.getEmployeeAssignments(this);
		double hoursRegistered=0;
		
		for (Assignment assignment:assignments) {
			for (WorkPeriod wp:assignment.timeRegisters) {
				if (wp.isDay(Util.getCurrentDay())) hoursRegistered+=wp.getLength();
			}
		}
		return hoursRegistered;
	}
		
	protected double hoursBooked (Database database, CalWeek start, CalWeek end) {
		double hoursBooked=0;
		List<Assignment> assignments = database.getEmployeeAssignments(this);
		
		for (Assignment assignment: assignments) {
			if (assignment.employeeID==this.ID) {
				for (WorkPeriod wp:assignment.bookings) {
					if (wp.isWhile(start,end)) hoursBooked+=wp.getLength(); 
				}
			}
		}
		return hoursBooked;
	}
	
	}
