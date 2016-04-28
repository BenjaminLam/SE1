package UI2;

import java.io.PrintWriter;

public class LoginScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Welcome to the tool for registration of hours and project management");
		System.out.println("Please enter your employee ID: ");
	}

	@Override
	public boolean processInput(String input, PrintWriter out) {
		return 
		
		
		switch (Integer.parseInt(input)) {
		case 1:
		}
		
	}

}
