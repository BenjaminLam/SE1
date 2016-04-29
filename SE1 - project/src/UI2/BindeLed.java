package UI2;

import project.*;


public class BindeLed {
	private LoginState loginState;
	private EmployeeState employeeState;
	private ProjectLeaderState projectLeaderState;
	private State currentState;
	private SubState currentSubState;
	private int stage;
	private boolean failed;
	
	Employee employee;
	boolean isProjectLeader;
	Database database;
	
	
	public BindeLed () {
		this.database=new Database();
		this.loginState=new LoginState();
		this.employeeState=new EmployeeState();;
		this.projectLeaderState==new ProjectLeaderState();
		this.
		
	}
	
	
	
	
	
	public boolean logIn(int EmpID) {
		Employee employee=database.getEmployee(EmpID);
		
		if (employee==null) return false;
		this.employee=employee;
		isProjectLeader=database.isProjectLeader(employee);
		return true;
	}
}
