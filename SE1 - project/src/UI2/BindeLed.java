package UI2;

import java.util.Observable;

import project.*;


public class BindeLed extends Observable {
	private SystemState currentState;
	private int subState;
	
	Database database;
	Employee employee;
	boolean isProjectLeader;
	
	public BindeLed () {
		this.currentState=SystemState.LoginState;
		this.subState=0;
		this.database=new Database();		
	}
	
	public boolean logIn(int EmpID) {
		Employee employee=database.getEmployee(EmpID);
		
		if (employee==null) return false;
		this.employee=employee;
		isProjectLeader=database.isProjectLeader(employee);
		return true;
	}

	public void init() {
	}
	public static void main(String[] args) {
		BindeLed bindeled=new BindeLed();
		bindeled.init();
	}
}
