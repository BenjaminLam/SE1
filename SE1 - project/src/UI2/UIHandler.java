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
//needs all register method in employee state
//register work: display list with bookings
//Notify user succes of operation

/*
 * Options in employee screen:
 * 		0) exit
		1) Register work
		2) Seek assistance
		3) Register vacation
		4) Register sickness
		5) Register course
		6) Create new project
		7) Set project leader
		8) Project leader page
		9) create new employee
		10) remove employee 
		11) Log off");
 */


public class UIHandler extends Observable {
	public ScreenState currentState;
	public int subState;
	private SysApp sysApp;
	List<? extends Object> listToProces; //list extracted from database, stored to process
	MyMap mapToProcess; //MyMap extracted from database, stored to process
	
	public UIHandler (MainUI mainUI) {
		this.subState=0;
		this.sysApp=new SysApp();
		sysApp.addObserver(mainUI);
	}
	
	public void init() throws WrongInputException {
		initDatabaseInfo();
		setState (ScreenState.LoginState);
	}
	//inits database with information. See SampleDataSetup0 in test package for description
	//this info differs from SampleDataSetup0 by using current day for bookings in stead of static day
	public void initDatabaseInfo() throws WrongInputException {
//		CalDay day=systemApp.getCurrentDay();
//		for (int i=0;i<10;i++){
//			Employee tempEmp=new Employee("Employee" + i,i);
//			systemApp.employees.add(tempEmp);
//			Project tempPro=new Project ("Project" + i,i);
//			systemApp.projects.add(tempPro);
//			Task tempTask=new Task(tempPro,"Task" + i);
//			systemApp.addTask(tempTask);
//			Assignment tempAss=new Assignment(tempTask,tempEmp);
//			systemApp.assignments.add(tempAss);
//			WorkPeriod tempWP=new WorkPeriod (day,9,9+i);
//			tempAss.bookings.add(tempWP);
//			
//			if(i==9) {
//				for (int j=0;j<6;j++) {
//					tempAss.bookings.add(new WorkPeriod(day,0,24));
//				}
//			}
//		}
//		systemApp.projects.get(0).projectLeader=systemApp.employees.get(0);
//		
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
		Boolean succesfull=sysApp.logIn(empID);
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
		}
		return false;
	}
	private boolean selectEmployeeSubstate (String userInput) {
		int userChoice=Integer.parseInt(userInput);
		if (userChoice<0 || userChoice>9) return false;
		if (userChoice==8) {
			if (!sysApp.isProjectLeader) return false;
			setStateResetSub(ScreenState.ProjectLeaderState);
			return true;
		}
		if (userChoice==9) logOff();
		setSubState(userChoice);
		return true;
	}
	private boolean registerWork(String userInput) throws WrongInputException{
		String[] userInputs=splitString(userInput);
		
		Employee currentEmp=sysApp.currentEmp;
		this.mapToProcess=sysApp.getTodaysBookings();
	
		switch (userInputs.length) {
		case 1: return registerWorkExcisting(userInputs); 
		case 3: return registerWorkToday(userInputs); 
		case 6: return registerWorkAnyDay(userInputs); 
		}
		
		return false;
	}
	private boolean registerWorkExcisting(String[] userinputs) {
		int userChoice=Integer.parseInt(userinputs[0]);
		Assignment assignment=(Assignment) mapToProcess.secondaryInfo.get(userChoice-1);
		WorkPeriod wp=(WorkPeriod) mapToProcess.mainInfo.get(userChoice-1);
		return sysApp.copyBookingToTimeRegister(wp, assignment);
	}
	private boolean registerWorkToday(String[] userInputs) throws WrongInputException {
		int taskID=Integer.parseInt(userInputs[0]);
		double start=Double.parseDouble(userInputs[1]);
		double end=Double.parseDouble(userInputs[2]);
		
		return sysApp.registerWorkManually(taskID, start, end, Util.getCurrentDay());
	}
	private boolean registerWorkAnyDay(String[] userInputs) throws WrongInputException {
		int taskID=Integer.parseInt(userInputs[0]);
		double start=Double.parseDouble(userInputs[1]);
		double end=Double.parseDouble(userInputs[2]);
		int year = Integer.parseInt(userInputs[3]);
		int week = Integer.parseInt(userInputs[4]);
		int weekDay = Integer.parseInt(userInputs[5]);
		CalDay day=new CalDay(new CalWeek(year,week),weekDay);
		
		return sysApp.registerWorkManually(taskID, start, end, day);
	}
	private boolean seekAssistance(String userInput) throws WrongInputException{
		String[] userInputs=splitString(userInput);
		
		switch(userInputs.length) {
		case 4: return seekAssistanceToday(userInputs);
		case 7: return seekAssistanceAnyDay(userInputs);
		}
		return false;
	}
	private boolean seekAssistanceToday (String[] userInputs) throws WrongInputException {
		int taskID=Integer.parseInt(userInputs[0]);
		int empID=Integer.parseInt(userInputs[1]);
		double start=Double.parseDouble(userInputs[2]);
		double end=Double.parseDouble(userInputs[3]);
		WorkPeriod wp=new WorkPeriod(Util.getCurrentDay(),start,end);
		
		return sysApp.seekAssistance(taskID, empID, wp);
	}
	private boolean seekAssistanceAnyDay (String[] userInputs) throws WrongInputException {
		int taskID=Integer.parseInt(userInputs[0]);
		int empID=Integer.parseInt(userInputs[1]);
		double start=Double.parseDouble(userInputs[2]);
		double end=Double.parseDouble(userInputs[3]);
		int year=Integer.parseInt(userInputs[4]);
		int week=Integer.parseInt(userInputs[5]);
		int weekDay=Integer.parseInt(userInputs[6]);
		WorkPeriod wp=new WorkPeriod(new CalDay(new CalWeek(year,week),weekDay),start,end);
		
		return sysApp.seekAssistance(taskID, empID, wp);
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
		return sysApp.createProject(userInput);
	}
	private boolean setProjectLeader(String userInput){
		String[] userInputs=splitString(userInput);
		if (userInputs.length!=2) return false;

		int projectID=Integer.parseInt(userInputs[0]);
		int employeeID=Integer.parseInt(userInputs[1]);		
		
		return sysApp.setProjectLeader(projectID, employeeID);
	}

	//Editing any of theese methods should also lead to change
	//in the options displayed in project leader screen in ui
	private boolean handleProjectLeaderState (String userInput) throws WrongInputException {
		switch (this.subState) {
		case 0: return selectProjectLeaderSubstate(userInput); 	
		case 1: return createTask(userInput);
		case 2: return setTaskBudgetTime(userInput); 
		case 3: return setTaskStart(userInput); 
		case 4: return setTaskEnd(userInput); 
		case 5: return employeesForTask(userInput); 
		case 6: return manTask (userInput); 
		}
		return false;
	}
	private boolean selectProjectLeaderSubstate(String userInput) {
		int userChoice=(Integer.parseInt(userInput));
		if (userChoice<1 || userChoice >7) return false;
		if (userChoice==7) setStateResetSub(ScreenState.EmployeeState); 
		else setSubState(userChoice);
		
		return true;
		
	}
	private boolean createTask (String userInput) throws WrongInputException{
		String[] userInputs=splitString(userInput);
		int projectID=Integer.parseInt(userInputs[0]);
		String taskName=userInputs[1];
		
		return sysApp.createTask(projectID,taskName);
	}
	private boolean setTaskBudgetTime(String userInput) throws WrongInputException {
		String[] userInputs=splitString(userInput);
		int taskID=Integer.parseInt(userInputs[0]);
		double timeBudget=Double.parseDouble(userInputs[1]);
		return sysApp.setTaskBudgetTime(taskID, timeBudget);
	}
	private boolean setTaskStart(String userInput) throws WrongInputException {
		String[] inputs=userInput.split(" ");
		
		switch (inputs.length) {
		case 2: return setTaskStartThisYear(inputs);
		case 3: return setTaskStartAnyYear(inputs);
		}
		
		return false;
	}
	private boolean setTaskStartThisYear(String[] inputs) throws WrongInputException{
		int taskID=Integer.parseInt(inputs[0]);
		int week=Integer.parseInt(inputs[1]);
		
		return sysApp.setTaskStart(taskID, Util.getCurrentYear(), week);
	}
	private boolean setTaskStartAnyYear (String[] inputs) throws WrongInputException {
		int taskID=Integer.parseInt(inputs[0]);
		int year=Integer.parseInt(inputs[1]);
		int week=Integer.parseInt(inputs[2]);
		return sysApp.setTaskStart(taskID,year,week);
	}
	private boolean setTaskEnd(String userInput) throws WrongInputException {
		String[] inputs=userInput.split(" ");
		
		switch (inputs.length) {
		case 2: return setTaskEndThisYear(inputs);
		case 3: return setTaskEndAnyYear(inputs);
		}
		
		return false;
	}
	private boolean setTaskEndThisYear(String[] inputs) throws WrongInputException{
		int taskID=Integer.parseInt(inputs[0]);
		int week=Integer.parseInt(inputs[1]);
		
		return sysApp.setTaskEnd(taskID, Util.getCurrentYear(), week);
	}
	private boolean setTaskEndAnyYear (String[] inputs) throws WrongInputException {
		int taskID=Integer.parseInt(inputs[0]);
		int year=Integer.parseInt(inputs[1]);
		int week=Integer.parseInt(inputs[2]);
		return sysApp.setTaskEnd(taskID,year,week);
	}
	private boolean employeesForTask(String userInput) throws WrongInputException {
		int userChoice=Integer.parseInt(userInput);
		this.listToProces=sysApp.employeesForTask(userChoice);
		setTempState(ScreenState.DisplayListState);
		return true;
	}
	private boolean manTask(String userInput) {
		String[] userInputs=splitString(userInput);
		int taskID=Integer.parseInt(userInputs[0]);
		int empID=Integer.parseInt(userInputs[1]);
		
		return sysApp.manTask(taskID,empID);
	}
	
	
	private void logOff() {
		setStateResetSub(ScreenState.LoginState);
	}
	
	private void setTempState (ScreenState state) {
		ScreenState normalState=this.currentState;
		setState(state);
		setState(normalState);
	}
	
	private void setStateResetSub (ScreenState state){
		this.subState=0;
		setState(state);
	}
	
	private void setState (ScreenState state) {
		this.currentState=state;
		this.setChanged();
		this.notifyObservers();
	}
	
	private void setSubState (int subState) {
		this.subState=subState;
		this.setChanged();
		this.notifyObservers();
	}
	
	private String[] splitString (String input) {
		return input.split(" ");
	}
}
