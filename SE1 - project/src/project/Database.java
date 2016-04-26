package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Exceptions_Errors.WrongInputException;

public class Database {
	public List<Employee> employees = new ArrayList<Employee>();
	public List<Project> projects = new ArrayList<Project>();
	private List<Task> tasks = new ArrayList<Task>();
	public List<Assignment> assignments=new ArrayList<Assignment>();
	
	public Database () {
	}
	
	public Task getTask (int taskID) {
		return tasks.get(taskID);
	}
	
	public Project getProject (int projectID) {
		for (Project project:projects){
			if (project.ID==projectID) return project;
		}
		return null;
	}
	
	public int getNumberOfTasks() {
		return tasks.size();
	}
	
	public boolean taskExists (int projectID, String name) {
		for (Task task:tasks) {
			if (task.name==name && task.projectID==projectID){
				return true;
			}
		}
		return false;
	}
	
	//not handling null input
	public int addTask(Task task){
		task.ID=tasks.size();
		tasks.add(task);
		return task.ID;
	}
	
	public double hoursBooked (Employee employee, CalWeek start, CalWeek end) {
		double hoursBooked=0;
		for (Assignment assignment: assignments) {
			if (assignment.employeeID==employee.ID) {
				for (WorkPeriod wp:assignment.bookings) {
					if (wp.isAfter(start) && wp.isBefore(end)) {
						hoursBooked+=wp.getLength();
					}
				}
			}
		}
		return hoursBooked;
	}
	
	public double hoursAvailable (Employee employee, CalWeek start, CalWeek end) {
		double availableTime=(end.week-start.week+1)*37.5;
		
		return availableTime-hoursBooked(employee,start,end);
	}
	
	public List<String> getAvailableEmployees (Employee mainEmployee, Task task) throws WrongInputException {
		if (task==null) throw new WrongInputException("Task doesn't excist");
		
		Project project=getProject(task.projectID);
		if (!project.isProjectLeader(mainEmployee)) throw new WrongInputException("You are not the project leader for the project of the task");
		
		if (task.start==null)throw new WrongInputException("Task doesn't have a start date");
		
		if (task.end==null)throw new WrongInputException("Task doesn't have an end date");
		
		List<String> employeesAvailable= new ArrayList<String>();
		
		for (Employee employee:employees) {
			double availableTime=hoursAvailable(employee,task.start,task.end);
			if (availableTime>0) employeesAvailable.add(employee.name + " hours free: " + availableTime);
		}
		
		return employeesAvailable;
	}
	
	
}
