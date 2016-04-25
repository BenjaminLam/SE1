package project;

public class Task {
	public String name;
	public int ID;
	public int projectID;
	public double timeBudget;
	CalWeek start;
	CalWeek end;
	
	public Task (Project project, String name) {
		this.name=name;
		this.projectID=project.ID;
	}
}
