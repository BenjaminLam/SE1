package project;

import java.util.ArrayList;
import java.util.List;

public class Assignment {
	public int taskID;
	public int employeeID;
	public List<WorkPeriod> bookings=new ArrayList<WorkPeriod>();
	private List<WorkPeriod> timeRegisters=new ArrayList<WorkPeriod>();
	
	public Assignment (Task task, Employee employee) {
		this.taskID=task.ID;
		this.employeeID=employee.ID;
	}
	
	public double getCumulativeTimeRegisters () {
		double cumulativeValue=0;
		
		for (WorkPeriod booking:timeRegisters) {
			cumulativeValue+=booking.getLength();
		}
		
		return cumulativeValue;
	}
}
