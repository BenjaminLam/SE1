package UI2;

import java.io.PrintWriter;

public abstract class Screen {
	private LoginScreen loginState;
	private EmployeeState employeeState;
	private ProjectLeaderState projectLeaderState;
	
	public abstract boolean processInput(String input, PrintWriter out);
	public abstract void printMenu(PrintWriter out);
}
