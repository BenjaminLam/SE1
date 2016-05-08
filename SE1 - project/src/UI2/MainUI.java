package UI2;

import java.io.BufferedReader;
import project.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Exceptions_Enums.ScreenState;
import Exceptions_Enums.WrongInputException;


//when user registers work for today print number of hours he has registered
//when user registers work print time bookings for today


public class MainUI implements Observer {
	private UIHandler uiHandler;
	private PrintStream out;
	private BufferedReader in;
	
	public MainUI () throws WrongInputException {
		this.uiHandler=new UIHandler(this);
		this.out=new PrintStream(System.out);
		this.in=new BufferedReader(new InputStreamReader(System.in));
		
		uiHandler.addObserver(this);
		uiHandler.init();
	}
	
	public void mainLoop () throws IOException, WrongInputException {
		while (true) {
			String userInput=in.readLine();
			uiHandler.handleInput(userInput);
		}
	}	
	
	@Override
	public void update(Observable O, Object arg) {
		switch (uiHandler.currentState) {
		case LoginState:
			displayLoginScreen();
		break;
		case EmployeeState:
			handleEmployeeScreen();
		break;
		case ProjectLeaderState:
			handleProjectLeaderScreen();
		break;
		case MessageState:
			handleMessage();
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
		case 9:  displayCreateEmployee (); break;
		case 10: displayRemoveEmployee (); break; 
		}
	}
	private void displayEmployeeScreen () {
		out.println("	0) exit");
		out.println("	1) Register work");
		out.println("	2) Seek assistance");
		out.println("	3) Register vacation");
		out.println("	4) Register sickness");
		out.println("	5) Register course");
		out.println("	6) Create new project");
		out.println("	7) Set project leader");
		out.println("	8) Project leader page");
		out.println("	9) create new employee");
		out.println("	10) remove employee ");
		out.println("	11) Log off");
        out.println("Select a number (0-11): ");
	}
	private void displayRegisterWork(){
		out.println("Select one of the following entries if you want to register your time according to your timebooking");
		out.println("If you want to manually register time, you have two options:"); 
		out.println("Register task today: input task id, start time and end time");
		out.println("Register task anyday: input task id, start time, end time, year, week, day of week"); 
		out.println("When registereing manually split words with space");
		
		List<String> printList=uiHandler.mapToProcess.asList();
		for (int i=0;i<printList.size();i++)  {
			out.println("1: " + printList.get(i));
		}
	}
	private void displaySeekAssistance(){
		out.println("Seek assistance today: input task id, employee id for employee you want help from, start time and end time");
		out.println("Seek assistance another day: input task id, employee id for employee you want help from, start time, end time, year, week and week day of the day you want help"); 
		out.println("Please split input words by a space");
	
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
		out.println("Please enter name of project to create");
	}
	private void displaySetProjectLeader(){
		out.println("Please enter project ID of project and employee ID of employee");
		out.println("Inputs should be seperated by a space");
	}
	private void displayCreateEmployee() {
		out.println("Please type name of new employee");
	}
	private void displayRemoveEmployee() {
		out.println("Please type employee ID of employee to be removed");
	}
	
	private void handleProjectLeaderScreen() {
		switch (uiHandler.subState) {
		case 0: displayProjectLeaderScreen(); break;
		case 1: displayRenameProject(); break;
		case 2: displaySetProjectStart(); break;
		case 3: displaySetProjectEnd (); break;
		case 4: displayRemoveProject(); break;
		case 5: displayCreatetask (); break;
		case 6: displaySetTaskBudgetTime(); break;
		case 7: displaySetTaskStart (); break;
		case 8: displaySetTaskEnd (); break;
		case 9: displayRemoveTask (); break;
		case 10: displayEmployeesForTask (); break;
		case 11: displayRenameTask (); break;
		case 12: displayManTask (); break;
		case 13: displayCreateBooking(); break;		
		case 14: displayRemoveBooking(); break;
		case 15: displayProjectReport(); break;
		case 16: displayTaskReport(); break;
		}
	}
	private void displayProjectLeaderScreen () {
		out.println("   0) exit");
		out.println("   1) Rename project");
		out.println("   2) Set project start");
		out.println("   3) Set project end");
		out.println("   4) Remove project");
		out.println("   5) Create task");
        out.println("   6) Set task budget time");
        out.println("   7) Set task start");
        out.println("   8) Set task end");
        out.println("   9) Delete task");
        out.println("   10) Get available employees for task");
        out.println("   11) Rename task");
        out.println("   12) Man task");
        out.println("   13) Create booking");
        out.println("   14) Remove booking");
        out.println("   15) Create project report");
        out.println("   16) Create task report");
        out.println("   17) back to employee screen");
        
        out.println("Select a number (0-17): ");
	}
	private void displayRenameProject() {
		
	}
	private void displaySetProjectStart(){
		
	}
	private void displaySetProjectEnd() {
		
	}
	private void displayRemoveProject() {
		
	}
	private void displayCreatetask () {
	
	}
	private void displaySetTaskBudgetTime () {
		out.println("Please enter task ID and budget time seperated by a space");
		explainDoubleTime();
	}	
	private void displaySetTaskStart () {
		out.println("Please enter task ID, task start year and task start week, each info seperated by a space");
	}	
	private void displaySetTaskEnd () {
		out.println("Please enter task ID, task end year and task end week, each info seperated by a space");
	}
	private void displayRemoveTask() {
		
	}
	private void displayEmployeesForTask () {
		out.println("Please enter ID for task to display employees for");
	}
	private void displayRenameTask() {
		
	}
	private void displayManTask () {
		out.println("Please enter task id and employee id seperated by a space");
	}
	private void displayCreateBooking() {
		
	}
	private void displayRemoveBooking() {
		
	}
	private void displayProjectReport() {
		
	}
	private void displayTaskReport() {
		
	}

	private void handleMessage() {
		for (String string:uiHandler.message) {
			out.println(string);
		}
	}

	private void explainDoubleTime() {
		out.println("Time can include half hours: eg input 9.5 for 9.30");
	}

	public static void main(String[] args) throws IOException, WrongInputException {
		MainUI minUI=new MainUI();
		minUI.mainLoop();
	}
	
}
