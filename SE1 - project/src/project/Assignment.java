package project;

import java.util.ArrayList;
import java.util.List;

public class Assignment {
	private int taskID;
	private int employeeID;
	private List<WorkPeriod> bookings=new ArrayList<WorkPeriod>();
	private List<WorkPeriod> timeRegisters=new ArrayList<WorkPeriod>();
	
	public Assignment (int taskID, int employeeID) {
		this.taskID=taskID;
		this.employeeID=employeeID;
	}
}
