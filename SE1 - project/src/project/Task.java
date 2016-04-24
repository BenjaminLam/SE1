package project;

public class Task {
	public String name;
	public int ID;
	public int projectID;
	public double timeBudget;
//	calWeek start;
//	calWeek end;
	
	public Task (int projectID, String name) {
		this.name=name;
		this.projectID=projectID;
	}
}
