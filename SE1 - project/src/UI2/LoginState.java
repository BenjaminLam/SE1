package UI2;

import java.io.PrintWriter;

public class LoginState extends Screen {
	BindeLed bindeled;
	
	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Welcome to the tool for registration of hours and project management");
		System.out.println("Please enter your employee ID: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) {
		return bindeled.logIn(Integer.parseInt(input));
	}

}
