package project;

public class Project {
	private String name;
	private int ID;
	public Employee projectLeader;
	private CalWeek start;
	private CalWeek end;
	
	public Project (String name, int ID) {
		this.name=name;
		this.ID=ID;
	}
}
