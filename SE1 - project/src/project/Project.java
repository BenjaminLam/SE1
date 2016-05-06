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
	
}
