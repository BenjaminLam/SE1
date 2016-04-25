package project;

public class Task {
	public String name;
	public int ID;
	public int projectID;
	private double timeBudget;
	private CalWeek start;
	private CalWeek end;
	
	public Task (Project project, String name) {
		this.name=name;
		this.projectID=project.ID;
	}
	
	
}
