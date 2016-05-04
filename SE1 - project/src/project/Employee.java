package project;

import java.util.ArrayList;
import java.util.List;

import Exceptions_Errors.*;

public class Employee {
	public String name;
	public int ID;
	
	public Employee (String name, int ID){
		this.name=name;
		this.ID=ID;
	}
	
	protected boolean isProjectLeader(Database database) {
		return database.isProjectLeader(this);
	}
	
	protected boolean isAvailable(WorkPeriod period,Database database ){
		for(Object object: dayBookings(period.day, database).mainInfo){
			WorkPeriod booking=(WorkPeriod) object;
			
			if(booking.overlapse(period)){
				return false;
			}
		}
		return true;
	}
	
	protected MyMap dayBookings(CalDay day, Database database){
		return database.getEmployeeDayBookings (this,day);
	}
	
	protected boolean createTask (Database database, int projectID, String name) throws WrongInputException {
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
		return database.addTask(new Task (project, name));
	}

	protected boolean manTask(Database database, int taskID, int employeeID) {
		Task task=database.getTask(taskID);
		if (task==null) return false;
		Employee employee=database.getEmployee(employeeID);
		if (employee==null) return false;
		Project project=database.getProject(task.projectID);
		if (project==null) return false;
		if (!project.isProjectLeader(this)) return false;
		
		if (database.getTask(taskID)==null) {
			return database.createAssignment(employee,task);
		}
		return false;
	}
	
	protected double hoursAvailable (Database database, CalWeek start, CalWeek end) {
		double availableTime=start.weeksBetween(end)*37.5;
		
		return availableTime-hoursBooked(database, start,end);
	}
	
	protected boolean setTaskBudgetTime (Database database, Task task, double timeBudget) {
		if (task==null) return false;
		Project project=database.getProject(task.projectID);
		if(!project.isProjectLeader(this)) return false;
		return task.setTimeBudget(timeBudget);
	}
	
	
	
	
	
	
	
	
	private double hoursBooked (Database database, CalWeek start, CalWeek end) {
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
	
	
	
	
	
	
	
	
	
	
	
	//not handling null value
	public boolean equals (Employee employee) {
		if (employee==null) return false;
		if (this.ID==employee.ID) return true;
		return false;
	}

	public boolean createAssignment (SysApp sysApp, Task task, Employee employee) throws WrongInputException {
		if (task==null) { 
			throw new WrongInputException("Task doesn't exist");	
		}
		if (employee==null) { 
			throw new WrongInputException("Employee doesn't exist");	
		}
		Project project=sysApp.getProject(task.projectID);
		
		if(!database.currentEmpIsProjectLeaderFor(project)) {
			throw new WrongInputException("You are not the project leader of the selected project");
		}
		if (sysApp.assignmentExists(task.ID, employee.ID)) {
			throw new WrongInputException("The assignment already exists in this project");
		}
		return sysApp.addAssignment(new Assignment (task, employee));
	}
		//checks if a employee is assigned a task.
	public boolean isAssigned (SysApp sysApp, Task task, Employee employee){
		if(sysApp.getAssignment(task.ID , employee)== null){
			return false;
		}
		return true;
	}
	
	
	
	
}
