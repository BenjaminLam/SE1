package UI2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import Enum_Interfaces_AbstractClasses.ScreenState;

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
	
	public void mainLoop () throws IOException {
		while (true) {
			String userInput=in.readLine();
			//if (Integer.parseInt(userInput)==0) break; //put somewhere else?
			uiHandler.handleInput(userInput);
		}
	}	
	
	@Override
	public void update(Observable arg0, Object arg1) {
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
		out.println("1");
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
		out.println("Project Leader Screen");
	}
	private void displayProjectLeaderScreen () {
	}

	public static void main(String[] args) throws IOException {
		MainUI minUI=new MainUI();
		minUI.mainLoop();
	}
	
	

	
}
