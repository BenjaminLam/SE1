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
		for (Task task:tasks){
			if (task.ID==taskID) return task;
		}
		return null;
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
}
