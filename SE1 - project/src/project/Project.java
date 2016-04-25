package project;

public class Project {
	private String name;
	public int ID;
	public Employee projectLeader;
	private CalWeek start;
	private CalWeek end;
	
	public Project (String name, int ID) {
		this.name=name;
		this.ID=ID;
	}
	
	//not handling null value
	public boolean isProjectLeader(Employee employee) {
		if (employee.equals(projectLeader)) return true;
		return false;
	}

}
