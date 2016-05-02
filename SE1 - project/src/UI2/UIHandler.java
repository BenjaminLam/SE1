package UI2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

import Enum_Interfaces_AbstractClasses.ScreenState;
import Exceptions_Errors.WrongInputException;
import project.*;

//notify user fail of operation

public class UIHandler extends Observable {
	public ScreenState currentState;
	public int subState;
	private Database database;
	private MainUI mainUI;
	List<? extends Object> listToProces; //list extracted from database, stored to process
	MyMap mapToProcess; //MyMap extracted from database, stored to process
	
	public UIHandler (MainUI mainUI) {
		this.mainUI=mainUI;
		this.subState=0;
		this.database=new Database();
		database.addObserver(mainUI);
	}
	
	public void init() throws WrongInputException {
		initDatabaseInfo();
		setState (ScreenState.LoginState);
	}
	//inits database with information. See SampleDataSetup0 in test package for description
	//this info differs from SampleDataSetup0 by using current day for bookings in stead of static day
	public void initDatabaseInfo() throws WrongInputException {
		CalDay day=database.getCurrentDay();
		for (int i=0;i<10;i++){
			Employee tempEmp=new Employee("Employee" + i,i);
			database.employees.add(tempEmp);
			Project tempPro=new Project ("Project" + i,i);
			database.projects.add(tempPro);
			Task tempTask=new Task(tempPro,"Task" + i);
			database.addTask(tempTask);
			Assignment tempAss=new Assignment(tempTask,tempEmp);
			database.assignments.add(tempAss);
			WorkPeriod tempWP=new WorkPeriod (day,9,9+i);
			tempAss.bookings.add(tempWP);
			
			if(i==9) {
				for (int j=0;j<6;j++) {
					tempAss.bookings.add(new WorkPeriod(day,0,24));
				}
			}
		}
		database.projects.get(0).projectLeader=database.employees.get(0);
		
	}
	
	public boolean handleInput(String userInput) throws WrongInputException {
		switch (currentState){
		case LoginState:
			return logIn(userInput);
		case EmployeeState:
			return handleEmployeeState(userInput);
		case ProjectLeaderState:
			return handleProjectLeaderState(userInput);
		}
		return false;
	}
	
	private boolean logIn(String userInput) {
		int empID=Integer.parseInt(userInput);
		Boolean succesfull=database.logIn(empID);
		if (succesfull)setState(ScreenState.EmployeeState);
		return succesfull;
	}
	
	//Editing any of theese methods should also lead to change
	//in the options displayed in employee screen in ui
	private boolean handleEmployeeState(String userInput) throws WrongInputException {
		switch (this.subState) {
		case 0: return selectEmployeeSubstate(userInput); 	
		case 1: return registerWork(userInput); 
		case 2: return seekAssistance(userInput); 
		case 3: return registerVacation(userInput); 
		case 4: return registerSickness(userInput); 
		case 5: return registerCourse (userInput); 
		case 6: return createNewProject (userInput); 
		case 7: return setProjectLeader (userInput); 
		case 8: setState(ScreenState.ProjectLeaderState); return true;
		}
		return false;
	}
	private boolean selectEmployeeSubstate (String userInput) {
		int userChoice=Integer.parseInt(userInput);
		if (userChoice<0 || userChoice>9) return false;
		if (userChoice==8) {
			if (!database.isProjectLeader) return false;
			setState(ScreenState.ProjectLeaderState);
			return true;
		}
		if (userChoice==9) logOff();
		setSubState(userChoice);
		return true;
	}
	private boolean registerWork(String userInput) throws WrongInputException{
		String[] userInputs=splitString(userInput);
		
		Employee currentEmp=database.currentEmp;
		CalDay currentDay=database.getCurrentDay();
		this.mapToProcess=database.employeesTodaysBookings(currentEmp, currentDay);
	
		switch (userInputs.length) {
		case 1: registerWorkExcisting(userInputs); break;
		case 3: registerWorkToday(userInputs); break;
		case 6: registerWorkAnyDay(userInputs); break;
		}
		
		return true;
	}
	private boolean registerWorkExcisting(String[] userinputs) {
		int userChoice=Integer.parseInt(userinputs[0]);
		Assignment assignment=(Assignment) mapToProcess.secondaryInfo.get(userChoice-1);
		WorkPeriod wp=(WorkPeriod) mapToProcess.mainInfo.get(userChoice-1);
		return database.copyBookingToTimeRegister(wp, assignment);
	}
	private boolean registerWorkToday(String[] userInputs) throws WrongInputException {
		int taskID=Integer.parseInt(userInputs[0]);
		double start=Double.parseDouble(userInputs[1]);
		double end=Double.parseDouble(userInputs[2]);
		CalDay currentDay=database.getCurrentDay();
		
		return database.registerWorkManually(taskID, start, end, currentDay);
	}
	private boolean registerWorkAnyDay(String[] userInputs) throws WrongInputException {
		int taskID=Integer.parseInt(userInputs[0]);
		double start=Double.parseDouble(userInputs[1]);
		double end=Double.parseDouble(userInputs[2]);
		int year = Integer.parseInt(userInputs[3]);
		int week = Integer.parseInt(userInputs[4]);
		int weekDay = Integer.parseInt(userInputs[5]);
		CalDay day=new CalDay(new CalWeek(year,week),weekDay);
		
		return database.registerWorkManually(taskID, start, end, day);
	}
	private boolean seekAssistance(String userInput){
		return false;
	}
	private boolean registerVacation(String userInput){
		return false;
	}
	private boolean registerSickness(String userInput){
		return false;
	}
	private boolean registerCourse (String userInput){
		return false;
	}
	private boolean createNewProject(String userInput) throws WrongInputException{		
		return createProject(userInput);
	}
	private boolean setProjectLeader(String userInput){
		return false;
	}

	//Editing any of theese methods should also lead to change
	//in the options displayed in project leader screen in ui
	private boolean handleProjectLeaderState (String userInput) throws WrongInputException {
		switch (this.subState) {
		case 0: return selectProjectLeaderSubstate(userInput); 	
		case 1: return setTaskBudgetTime(userInput); 
		case 2: return setTaskStart(userInput); 
		case 3: return setTaskEnd(userInput); 
		case 4: return employeesForTask(userInput); 
		case 5: return manTask (userInput); 
		case 6: setState(ScreenState.EmployeeState); return true; 
		}
		return false;
	}
	private boolean selectProjectLeaderSubstate(String userInput) {
		int userChoice=(Integer.parseInt(userInput));
		if (userChoice<1 || userChoice >7) return false;
		if (userChoice==7) setState(ScreenState.EmployeeState); 
		else setSubState(userChoice);
		
		return true;
		
	}
	private boolean setTaskBudgetTime(String userInput) throws WrongInputException {
		//ændre metode til at bruge fizbjørns
		String[] inputs=userInput.split(" ");
		if (inputs.length<2) return false;
		int taskID=Integer.parseInt(inputs[0]);
		Task task=database.getTask(taskID);
		if (task==null) return false;
		double time=Double.parseDouble(inputs[1]);
		task.timeBudget=time;
		database.changed("Timebudget for task: " + task.name + " changed succesfully to " + time);
		return true;
	}
	private boolean setTaskStart(String userInput) throws WrongInputException {
		//ændre metode til at bruge fizbjørns
		String[] inputs=userInput.split(" ");
		if (inputs.length<3) return false;
		int taskID=Integer.parseInt(inputs[0]);
		Task task=database.getTask(taskID);
		if (task==null) return false;
		int year=Integer.parseInt(inputs[1]);
		int week=Integer.parseInt(inputs[2]);
		CalWeek calWeek=new CalWeek(year, week);
		task.start=calWeek;
		database.changed("Start time for task: " + task.name + " changed succesfully to " + calWeek );
		return true;
	}
	private boolean setTaskEnd(String userInput) throws WrongInputException {
		//ændre metode til at bruge fizbjørns
		String[] inputs=userInput.split(" ");
		if (inputs.length<3) return false;
		int taskID=Integer.parseInt(inputs[0]);
		Task task=database.getTask(taskID);
		if (task==null) return false;
		int year=Integer.parseInt(inputs[1]);
		int week=Integer.parseInt(inputs[2]);
		CalWeek calWeek=new CalWeek(year, week);
		task.end=calWeek;
		database.changed("End time for task: " + task.name + " changed succesfully to " + calWeek );
		return true;
	}
	private boolean employeesForTask(String userInput) throws WrongInputException {
		int taskID=Integer.parseInt(userInput);
		Task task=database.getTask(taskID);
		if (task==null) return false;
		this.listToProces=database.getAvailableEmployees(database.currentEmp, task);
		
		this.setState(ScreenState.DisplayListState);
		this.setState(ScreenState.ProjectLeaderState);
		
		return true;
	}
	private boolean manTask(String userInput) {
		return false;
	}
	
		
	
	
	private void logOff() {
		setState(ScreenState.LoginState);
	}
	
	public void setState (ScreenState state){
		this.subState=0;
		this.currentState=state;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setSubState (int subState) {
		this.subState=subState;
		this.setChanged();
		this.notifyObservers();
	}
	
	private boolean createProject (String name) throws WrongInputException {
			return database.createProject(name);
	}

	private String[] splitString (String input) {
		return input.split(" ");
	}
}
