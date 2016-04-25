package project;

public class Employee {
	String name;
	int ID;
	
	public Employee (String name, int ID){
		this.name=name;
		this.ID=ID;
	}
	
	//not test implemented
	//not handling null value
	public boolean equals (Employee employee) {
		if (this.ID==employee.ID) return true;
		return false;
	}
	
	public int createTask (Database database, int projectID, String name) {
		Task task=new Task (projectID, name);
		database.tasks.add(task);
		return database.tasks.indexOf(task);
	}
}
