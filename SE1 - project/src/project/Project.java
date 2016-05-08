package project;

import java.util.ArrayList;
import java.util.List;

import Exceptions_Enums.WrongInputException;

public class Project {
	public String name;
	public int ID;
	public Employee projectLeader;
	public CalWeek start;
	public CalWeek end;
	
	public Project (String name) {
		this.name=name;
	}
	
	//not handling null value
	public boolean isProjectLeader(Employee employee) {
		if (employee.equals(projectLeader)) return true;
		return false;
	}

	public double hoursProjected (Database database) throws WrongInputException {
		double hoursProjected=0;
		List<Task> projectTasks=database.projectGetTasks(this);
		
		for (Task task:projectTasks) {
			if (task.projectID==this.ID) hoursProjected+=task.timeBudget;
		}
		return hoursProjected;
	} 
	
	public double hoursSpent(Database database) throws WrongInputException {
		double hoursSpent=0;
	
		List<Task> projectTasks=database.projectGetTasks(this);
		
		for (Task task:projectTasks) {
			for (Assignment assignment:database.assignments) {
				if (assignment.taskID==task.ID) hoursSpent+=assignment.getCumulativeTimeRegisters();
			}
		}
		
		return hoursSpent;
	}
	
	public List<Task> completedTasks (Database database) throws WrongInputException {
		List<Task> completedTasks=new ArrayList<Task>();
		List<Task> projectTasks=database.projectGetTasks(this);
		
		for (Task task:projectTasks) {
			try {
				if(task.inPast()) completedTasks.add(task);
			} catch (WrongInputException e) {
				
			}
		}
		
		return completedTasks;
	}
	
	public List<Task> remainingTasks (Database database) throws WrongInputException {
		List<Task> projectTasks=database.projectGetTasks(this);
		List<Task> completedTasks=completedTasks(database);
		List<Task> remainingTasks=new ArrayList<Task>();
		
		for (Task task:projectTasks) {
			if (!completedTasks.contains(task)) remainingTasks.add(task); 
		}
		return remainingTasks;
	}
	
	public Project setStart (CalWeek start, Employee employee, Database database) throws WrongInputException {
		if (!this.isProjectLeader(employee)) throw new WrongInputException ("You are not the project leader of this project");
		
		for (Task task:database.projectGetTasks(this)) {
			if (task.start.isBefore(start)) throw new WrongInputException ("One or more of the projects tasks has a start date before your entered project start date");
		}
		
		if (this.end!=null && this.end.isBefore(start)) throw new WrongInputException ("The end date of the project is before the selected start date");
		
		if (start.isBefore(Util.getCurrentWeek())) throw new WrongInputException ("You can't select a start date which lies in the past");
		
		this.start=start;
		return this;
	}
	
	public Project setEnd (CalWeek end, Employee employee, Database database) throws WrongInputException {
		if (!this.isProjectLeader(employee)) throw new WrongInputException ("You are not the project leader of this project");
		
		for (Task task:database.projectGetTasks(this)) {
			if (task.end.isAfter(end)) throw new WrongInputException ("One or more of the projects tasks has an end date after your entered project end date");
		}
		
		if (this.start!=null && this.start.isAfter(end)) throw new WrongInputException ("You can't select an end date which is before the projects start date");
		
		if (end.isBefore(Util.getCurrentWeek())) throw new WrongInputException ("You can't select an end date which lies in the past");
		
		this.end=end;
		return this;
	}
}
