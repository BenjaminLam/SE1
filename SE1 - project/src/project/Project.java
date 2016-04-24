package project;

public class Project {
	private String name;
	private int ID;
	public Employee projectLeader;
//	private calWeek start;
//	private calWeek end;
	
	public Project (String name, int ID) {
		this.name=name;
		this.ID=ID;
	}
	
	public void setProjectLeader (Employee employee) {
		if (employee==null) throw new IllegalArgumentException("baaah");
		projectLeader=employee;
	}
}
