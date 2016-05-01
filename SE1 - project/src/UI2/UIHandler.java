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
	private Employee employee;
	boolean isProjectLeader;
	List<? extends Object> listToProces;
	MyMap mapToProcess;
	
	public UIHandler (MainUI mainUI) {
		this.mainUI=mainUI;
		this.subState=0;
		this.database=new Database();
		database.addObserver(mainUI);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void init() {
		setState (ScreenState.LoginState);
	}
	
	public boolean handleInput(String userInput) throws WrongInputException {
		switch (currentState){
		case LoginState:
			return logIn(Integer.parseInt(userInput));
		case EmployeeState:
			return handleEmployeeState(userInput);
		case ProjectLeaderState:
			return handleProjectLeaderState(userInput);
		}
		return false;
	}
	
	public boolean logIn(int EmpID) {
		Employee employee=database.getEmployee(EmpID);
		
		//if (employee==null) return false;
		this.employee=employee;
		isProjectLeader=database.isProjectLeader(employee);
		setState(ScreenState.EmployeeState);
		return true;
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
			//if (!isProjectLeader) return false;
			setState(ScreenState.ProjectLeaderState);
			return true;
		}
		if (userChoice==9) logOff();
		setSubState(userChoice);
		return true;
	}
	private boolean registerWork(String userInput) throws WrongInputException{
		String[] userInputs=userInput.split(" ");
		int userChoice=Integer.parseInt(userInputs[0]);
		this.mapToProcess=database.employeesTodaysBookings(employee, getCurrentDay());
	
		WorkPeriod wp=null;
		if (userChoice==mapToProcess.mainInfo.size()+1) {
			int taskID=Integer.parseInt(userInputs[1]);
			double start=Double.parseDouble(userInputs[2]);
			double end=Double.parseDouble(userInputs[3]);
			Assignment assignment=database.getAssignment(taskID, this.employee);
			if (assignment==null) return false;
			wp=new WorkPeriod (getCurrentDay(), start, end);
			assignment.timeRegisters.add(wp);
		}
		else {
			if (userChoice>=1 && userChoice<=mapToProcess.mainInfo.size() ) {
				Assignment assignment=(Assignment) mapToProcess.secondaryInfo.get(userChoice-1);
				wp=(WorkPeriod) mapToProcess.mainInfo.get(userChoice-1);
				assignment.timeRegisters.add(wp);
			}
		}
		database.changed(wp);
		return true;
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
	private boolean handleProjectLeaderState (String userInput) {
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
	private boolean setTaskBudgetTime(String userInput) {
		return false;
	}
	private boolean setTaskStart(String userInput) {
		return false;
	}
	private boolean setTaskEnd(String userInput) {
		return false;
	}
	private boolean employeesForTask(String userInput) {
		return false;
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

	private String[] seperateString (String input) {
		return input.split(" ");
	}

	private CalDay getCurrentDay () {
		Calendar calendar=new GregorianCalendar();
		int year=calendar.get(Calendar.YEAR);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		int day=calendar.get(Calendar.DAY_OF_WEEK);
		
		return new CalDay(new CalWeek(year,week),day);
	}
}
