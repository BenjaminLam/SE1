package project;

import java.util.ArrayList;
import java.util.List;

public class Assignment {
	private int taskID;
	public int employeeID;
	public List<WorkPeriod> bookings=new ArrayList<WorkPeriod>();
	private List<WorkPeriod> timeRegisters=new ArrayList<WorkPeriod>();
	
	public Assignment (Task task, Employee employee) {
		this.taskID=task.ID;
		this.employeeID=employee.ID;
	}
}
