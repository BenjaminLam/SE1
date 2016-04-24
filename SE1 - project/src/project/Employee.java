package project;

public class Employee {
	String name;
	int ID;
	
	public Employee (String name, int ID){
		this.name=name;
		this.ID=ID;
	}
	
	public boolean isProjectLeader (Database database) {
		return database.isProjectLeader(this);
	}
	
	public boolean equals (Employee employee) {
		if (this.ID==employee.ID) return true;
		return false;
	}
	
	public void createTask (Database database, int projectID, String name) {
		database.tasks.add(new Task (projectID, name));
	}
}
