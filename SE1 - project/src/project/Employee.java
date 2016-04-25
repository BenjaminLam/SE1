package project;

import Exceptions_Errors.*;

public class Employee {
	private String name;
	public int ID;
	
	public Employee (String name, int ID){
		this.name=name;
		this.ID=ID;
	}
	
	//not handling null value
	public boolean equals (Employee employee) {
		if (this.ID==employee.ID) return true;
		return false;
	}
	
	//not handling null name or null database
	public int createTask (Database database, Project project, String name) throws WrongInputException {
		if (project==null) { 
			throw new WrongInputException("Project doesn't exist");	
		}
		if(!project.isProjectLeader(this)) {
			throw new WrongInputException("You are not the project leader of the selected project");
		}
		if (database.taskExists(project.ID, name)) {
			throw new WrongInputException("The task already exists in this project");
		}
		return database.addTask(new Task (project, name));
	}

	
}
