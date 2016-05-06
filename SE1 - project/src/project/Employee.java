package project;

import java.util.ArrayList;
import java.util.List;

import Exceptions_Enums.*;

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
		
		Project project=database.getProject(task.projectID);
		if (project==null) throw new WrongInputException("No project exists corresponding to that task");
		
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

		//checks if a employee is assigned a task.
	public boolean isAssigned (Database database, Task task, Employee employee){
		if(database.getAssignment(task.ID , employee.ID)== null){
			return false;
		}
		return true;
	}
	
	public Employee setSickness (Database database, Employee employee){
		int taskID = 0;
		int employeeID = employee.ID;
		WorkPeriod wp = new WorkPeriod(Util.getCurrentDay(),9.0,16.0);
		for (Assignment ass:database.assignments) {
			if(employeeID == ass.employeeID && ass.taskID==taskID){
				ass.addBooking(wp);
			}
		}
		return ;
	}	
	
	public 
	
}
