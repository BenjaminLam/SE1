package UI;

import project.*;


public class BindeLed {
	Employee currentEmp;
	
	Database database;
	UI UI=new UI();
	
	
	public BindeLed() {
		
	}
	
	public boolean logIn(int EmpID) {
		Employee employee=database.getEmployee(EmpID);
		
		if (employee==null) return false;
		this.currentEmp=employee;
		return true;
	}
}
