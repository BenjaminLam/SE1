package project;

public class Project {
	public String name;
	public int ID;
	public Employee projectLeader;
	public CalWeek start;
	public CalWeek end;
	
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
