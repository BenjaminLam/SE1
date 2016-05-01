package UI2;

import java.io.BufferedReader;
import project.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import Enum_Interfaces_AbstractClasses.ScreenState;
import Exceptions_Errors.WrongInputException;

public class MainUI implements Observer {
	private UIHandler uiHandler;
	private PrintStream out;
	private BufferedReader in;
	
	public MainUI () {
		this.uiHandler=new UIHandler(this);
		this.out=new PrintStream(System.out);
		this.in=new BufferedReader(new InputStreamReader(System.in));
		
		uiHandler.addObserver(this);
		uiHandler.init();
	}
	
	public void mainLoop () throws IOException, WrongInputException {
		while (true) {
			String userInput=in.readLine();
			//if (Integer.parseInt(userInput)==0) break; //put somewhere else?
			uiHandler.handleInput(userInput);
		}
	}	
	
	@Override
	public void update(Observable O, Object arg) {
		if (arg==null) {
			ScreenState currentState=uiHandler.currentState;
			
			switch (currentState) {
			case LoginState:
				displayLoginScreen();
			break;
			case EmployeeState:
				handleEmployeeScreen();
			break;
			case ProjectLeaderState:
				handleProjectLeaderScreen();
			break;
			}
		}
		else {
			if (arg instanceof Project) {
				out.println("Created project succesfully");
				out.print("Project name: "); out.println(((Project) arg).name);
				out.print("Project ID: "); out.println(((Project) arg).ID);
			}
			if (arg instanceof WorkPeriod) {
				out.println("Information registered succesfully");
			}
		}
		
	}
	
	private void displayLoginScreen () {
		out.println("Welcome to the tool for registration of hours and project management");
		out.println("Please enter your employee ID: ");
	}
	
	private void handleEmployeeScreen () {
		switch (uiHandler.subState) {
		case 0:  displayEmployeeScreen(); break;
		case 1:  displayRegisterWork(); break;
		case 2:  displaySeekAssistance(); break;
		case 3:  displayRegisterVacation(); break;
		case 4:  displayRegisterSickness(); break;
		case 5:  displayRegisterCourse (); break;
		case 6:  displayCreateNewProject (); break;
		case 7:  displaySetProjectLeader (); break;
		}
	}
	private void displayEmployeeScreen () {
		out.println("   0) exit");
        out.println("   1) Register work");
        out.println("   2) Seek assistance");
        out.println("   3) Register vacation");
        out.println("   4) Register sickness");
        out.println("   5) Register course");
        out.println("   6) Create new project");
        out.println("   7) Set project leader");
        out.println("   8) Change project");
        out.println("   9) Log off");
        out.println("Select a number (0-9): ");
	}
	private void displayRegisterWork(){
		out.println("Select one of the following entries if you want to register your time according to your timebooking");
		out.println("If you want to manually register time, input number for manual input followed by task id, start time and end time");
		out.println("When registereing manually split words with space");
	
	}
	private void displaySeekAssistance(){
		out.println("2");
	}
	private void displayRegisterVacation(){
		out.println("3");
	}
	private void displayRegisterSickness(){
		out.println("4");
	}
	private void displayRegisterCourse (){
		out.println("5");
	}
	private void displayCreateNewProject(){
		out.println("Please enter name of project and project id seperated by a space");
	}
	private void displaySetProjectLeader(){
		out.println("7");
	}
	
	private void handleProjectLeaderScreen() {
		switch (uiHandler.subState) {
		case 0: displayProjectLeaderScreen(); break;
		case 1: displaySetTaskBudgetTime(); break;
		case 2: displayCreatetask (); break;
		case 3: displaySetTaskStart (); break;
		case 4: displaySetTaskEnd (); break;
		case 5: displayEmployeesForTask (); break;
		case 6: displayManTask(); break;
		}
	}
	private void displayProjectLeaderScreen () {
		out.println("   0) exit");
        out.println("   1) Create task");
        out.println("   2) Set task budget time");
        out.println("   3) Set task start");
        out.println("   4) Set task end");
        out.println("   5) Get available employees for task");
        out.println("   6) Man task");
        out.println("Select a number (0-9): ");
	}
	private void displayCreatetask () {
		
	}
	private void displaySetTaskBudgetTime () {
		out.println("Please enter task ID and budget time seperated by a space");
	}	
	private void displaySetTaskStart () {
		out.println("Please enter task ID and start time seperated by a space");
		explainDoubleTime();
	}	
	private void displaySetTaskEnd () {
		out.println("Please enter task ID and end time seperated by a space");
		explainDoubleTime();
	}
	private void displayEmployeesForTask () {
		out.println("Please enter ID for task to display employees for");
	}
	private void displayManTask () {
		out.println("Please enter task id and employee id seperated by a space");
	}
	
	private void explainDoubleTime() {
		out.println("Time can include half hours: eg input 9.5 for 9.30");
	}

	
	
	public static void main(String[] args) throws IOException, WrongInputException {
		MainUI minUI=new MainUI();
		minUI.mainLoop();
	}
	
	

	
}
