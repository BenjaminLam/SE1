package UI2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

import Exceptions_Enums.*;
import project.*;

//notify user fail of operation
//needs all register method in employee state
//register work: display list with bookings
//Notify user succes of operation
//when creating new employee/project/task: paste the id of the project


public class UIHandler extends Observable {
	public ScreenState currentState;
	public int subState;
	private SysApp sysApp;
	public List<? extends Object> listToProces; //list extracted from database, stored to process
	public MyMap mapToProcess; //MyMap extracted from database, stored to process
	public String errorMessage;
	
	public UIHandler (MainUI mainUI) {
		this.subState=0;
		this.sysApp=new SysApp();
		sysApp.addObserver(mainUI);
	}
	
	public void init()  {
		setState (ScreenState.LoginState);
	}

	public void handleInput(String userInput) {
		switch (currentState){
		case LoginState:
			logIn(userInput); break;
		case EmployeeState:
			handleEmployeeState(userInput); break;
		case ProjectLeaderState:
			handleProjectLeaderState(userInput); break;
		default:
			terminate();
		}
	}
	
	private void logIn(String userInput) {
		int empID=Integer.parseInt(userInput);
		try {
			sysApp.logIn(empID);
			setState(ScreenState.EmployeeState);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	
	//Editing any of theese methods should also lead to change
	//in the options displayed in employee screen in ui
	private void handleEmployeeState(String userInput)  {
		switch (this.subState) {
		case 0:  selectEmployeeSubstate(userInput); break; 	
		case 1:  registerWork(userInput); break; 
		case 2:  seekAssistance(userInput); break; 
		case 3:  registerVacation(userInput); break; 
		case 4:  registerSickness(userInput); break; 
		case 5:  registerCourse (userInput); break; 
		case 6:  createProject (userInput); break; 
		case 7:  setProjectLeader (userInput); break;
		case 9:  createEmployee (userInput); break;
		case 10:  removeEmployee (userInput); break;
		default: terminate();
		}
		
	}
	private void selectEmployeeSubstate (String userInput) {
		int userChoice=Integer.parseInt(userInput);
		
		try {
			if (userChoice<0 || userChoice>11) {
				throw new WrongInputException ("Illegal choice");
			}
		} catch (WrongInputException e) {
			error(e.getMessage());
			return;
		}
		
		if (userChoice==8) {
			try {
				if (!sysApp.isProjectLeader) throw new WrongInputException ("You are not a project leader");
				setStateResetSub(ScreenState.ProjectLeaderState);
				return;
			} catch (WrongInputException e) {
				error(e.getMessage());
				return;
			}
		}
		
		if (userChoice==11) logOff();
		setSubState(userChoice);
	}
	private void registerWork(String userInput){
		String[] userInputs=Util.splitString(userInput);
		
		Employee currentEmp=sysApp.currentEmp;
		this.mapToProcess=sysApp.getTodaysBookings();
	
		switch (userInputs.length) {
		case 1: registerWorkExcisting(userInputs); break;
		case 3: registerWorkToday(userInputs); break;
		case 6: registerWorkAnyDay(userInputs); break;
		default: wrongInputFormat(); break;
		}
	}
	private void registerWorkExcisting(String[] userinputs) {
		int userChoice=Integer.parseInt(userinputs[0]);
		Assignment assignment=(Assignment) mapToProcess.secondaryInfo.get(userChoice-1);
		WorkPeriod wp=(WorkPeriod) mapToProcess.mainInfo.get(userChoice-1);
		try {
			sysApp.copyBookingToTimeRegister(wp, assignment);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void registerWorkToday(String[] userInputs) {
		int taskID=Integer.parseInt(userInputs[0]);
		double start=Double.parseDouble(userInputs[1]);
		double end=Double.parseDouble(userInputs[2]);
		
		try {
			sysApp.registerWorkManually(taskID, start, end, Util.getCurrentDay());
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void registerWorkAnyDay(String[] userInputs) {
		int taskID=Integer.parseInt(userInputs[0]);
		double start=Double.parseDouble(userInputs[1]);
		double end=Double.parseDouble(userInputs[2]);
		int year = Integer.parseInt(userInputs[3]);
		int week = Integer.parseInt(userInputs[4]);
		int weekDay = Integer.parseInt(userInputs[5]);
		CalDay day=new CalDay(new CalWeek(year,week),weekDay);
		
		try {
			sysApp.registerWorkManually(taskID, start, end, day);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void seekAssistance(String userInput) {
		String[] userInputs=Util.splitString(userInput);
		
		switch(userInputs.length) {
		case 4: seekAssistanceToday(userInputs); break;
		case 7: seekAssistanceAnyDay(userInputs); break;
		default: wrongInputFormat(); break;
		}
	}
	private void seekAssistanceToday (String[] userInputs) {
		int taskID=Integer.parseInt(userInputs[0]);
		int empID=Integer.parseInt(userInputs[1]);
		double start=Double.parseDouble(userInputs[2]);
		double end=Double.parseDouble(userInputs[3]);
		WorkPeriod wp=null;
		try {
			wp = new WorkPeriod(Util.getCurrentDay(),start,end);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
		
		try {
			sysApp.seekAssistance(taskID, empID, wp);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void seekAssistanceAnyDay (String[] userInputs)  {
		int taskID=Integer.parseInt(userInputs[0]);
		int empID=Integer.parseInt(userInputs[1]);
		double start=Double.parseDouble(userInputs[2]);
		double end=Double.parseDouble(userInputs[3]);
		int year=Integer.parseInt(userInputs[4]);
		int week=Integer.parseInt(userInputs[5]);
		int weekDay=Integer.parseInt(userInputs[6]);
		WorkPeriod wp=null;
		try {
			wp = new WorkPeriod(new CalDay(new CalWeek(year,week),weekDay),start,end);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
		
		try {
			sysApp.seekAssistance(taskID, empID, wp);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void registerSickness(String userInput){
	}
	private void registerVacation(String userInput){
	}
	private void registerCourse (String userInput){
	}
	private void createProject(String userInput){		
		try {
			sysApp.createProject(userInput);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setProjectLeader(String userInput){
		String[] userInputs=Util.splitString(userInput);
		if (userInputs.length!=2) {
			wrongInputFormat();
			return;
		}

		int projectID=Integer.parseInt(userInputs[0]);
		int employeeID=Integer.parseInt(userInputs[1]);		
		
		try {
			sysApp.setProjectLeader(projectID, employeeID);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void createEmployee (String userInput) {
		try {
			sysApp.createEmployee(userInput);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void removeEmployee (String userInput) {
		int empID=Integer.parseInt(userInput);
		try {
			sysApp.removeEmployee(empID);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	
	//Editing any of theese methods should also lead to change
	//in the options displayed in project leader screen in ui
	private void handleProjectLeaderState (String userInput) {
		switch (this.subState) {
		case 0: selectProjectLeaderSubstate(userInput); break; 	
		case 1: renameProject(userInput); break;
		case 2: setProjectStart(userInput); break; 
		case 3: setProjectEnd(userInput); break;
		case 4: removeProject (userInput); break;
		case 5: createTask(userInput); break;
		case 6: setTaskBudgetTime(userInput); break; 
		case 7: setTaskStart(userInput); break; 
		case 8: setTaskEnd(userInput); break; 
		case 9: removeTask(userInput); break; 
		case 10: employeesForTask(userInput); break; 
		case 11: renameTask(userInput); break;
		case 12: manTask (userInput); break; 
		case 13: createBooking (userInput); break;
		case 14: removeBooking (userInput); break;
		case 15: createProjectReport (userInput); break;
		case 16: createTaskReport (userInput); break;
		default: terminate();
		}
	}
	private void selectProjectLeaderSubstate(String userInput) {
		int userChoice=(Integer.parseInt(userInput));
		try {
			if (userChoice<1 || userChoice >17) throw new WrongInputException ("Illegal choice");
		}
		catch (WrongInputException e) {
			error(e.getMessage());
			return;
		}
		if (userChoice==17) setStateResetSub(ScreenState.EmployeeState); 
		else setSubState(userChoice);
	}
	private void renameProject (String userInput) {
		String[] userInputs=Util.splitString(userInput);
		if (userInputs.length!=2) {
			wrongInputFormat();
			return;
		}
		int projectID=Integer.parseInt(userInputs[0]);
		String name=userInputs[1];
		try {
			sysApp.renameProject(projectID,name);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setProjectStart (String userInput) {
		
	}
	private void setProjectEnd (String userInput) {
		
	}
	private void removeProject (String userInput) {
		int projectID=Integer.parseInt(userInput);
		try {
			sysApp.removeProject(projectID);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void createTask (String userInput) {
		String[] userInputs=Util.splitString(userInput);
		if (userInputs.length!=2) {
			wrongInputFormat();
			return;
		}
		
		int projectID=Integer.parseInt(userInputs[0]);
		String taskName=userInputs[1];
		
		try {
			sysApp.createTask(projectID,taskName);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setTaskBudgetTime(String userInput) {
		String[] userInputs=Util.splitString(userInput);
		if (userInputs.length!=2) {
			wrongInputFormat();
			return;
		}
		
		int taskID=Integer.parseInt(userInputs[0]);
		double timeBudget=Double.parseDouble(userInputs[1]);
		try {
			sysApp.setTaskBudgetTime(taskID, timeBudget);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setTaskStart(String userInput) {
		String[] inputs=userInput.split(" ");
		
		switch (inputs.length) {
		case 2: setTaskStartThisYear(inputs); break;
		case 3: setTaskStartAnyYear(inputs); break;
		default: wrongInputFormat(); break;
		}
	}
	private void setTaskStartThisYear(String[] inputs){
		int taskID=Integer.parseInt(inputs[0]);
		int week=Integer.parseInt(inputs[1]);
		
		try {
			sysApp.setTaskStart(taskID, Util.getCurrentYear(), week);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setTaskStartAnyYear (String[] inputs) {
		int taskID=Integer.parseInt(inputs[0]);
		int year=Integer.parseInt(inputs[1]);
		int week=Integer.parseInt(inputs[2]);
		try {
			sysApp.setTaskStart(taskID,year,week);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setTaskEnd(String userInput) {
		String[] inputs=userInput.split(" ");
		
		switch (inputs.length) {
		case 2: setTaskEndThisYear(inputs); break;
		case 3: setTaskEndAnyYear(inputs); break;
		default: wrongInputFormat(); break;
		}
	}
	private void setTaskEndThisYear(String[] inputs){
		int taskID=Integer.parseInt(inputs[0]);
		int week=Integer.parseInt(inputs[1]);
		
		try {
			sysApp.setTaskEnd(taskID, Util.getCurrentYear(), week);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void setTaskEndAnyYear (String[] inputs) {
		int taskID=Integer.parseInt(inputs[0]);
		int year=Integer.parseInt(inputs[1]);
		int week=Integer.parseInt(inputs[2]);
		try {
			sysApp.setTaskEnd(taskID,year,week);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void removeTask(String userInput) {
		int taskID=Integer.parseInt(userInput);
		try {
			sysApp.removeTask(taskID);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void employeesForTask(String userInput) {
		int taskID=Integer.parseInt(userInput);
		try {
			this.listToProces=sysApp.employeesForTask(taskID);
			setTempState(ScreenState.DisplayListState);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void renameTask (String userInput) {
		
	}
	private void manTask(String userInput) {
		String[] userInputs=Util.splitString(userInput);
		if (userInputs.length!=2) {
			wrongInputFormat();
			return;
		}
		
		int taskID=Integer.parseInt(userInputs[0]);
		int empID=Integer.parseInt(userInputs[1]);
		
		try {
			sysApp.manTask(taskID,empID);
		} catch (WrongInputException e) {
			error (e.getMessage());
		}
	}
	private void createBooking (String userInput) {
		
	}
	private void removeBooking (String userInput) {
		
	}
	private void createProjectReport (String userInput) {
		int projectID=Integer.parseInt(userInput);
		try {
			sysApp.createProjectReport(projectID);
		} catch (WrongInputException e) {
			error(e.getMessage());
		}
	}
	private void createTaskReport (String userInput) {
		
	}
	
	private void logOff() {
		setStateResetSub(ScreenState.LoginState);
	}
	
	private void terminate() {
		
	}
	
	private void error (String errorMessage) {
		if (errorMessage==null) terminate();
		this.errorMessage=errorMessage;
		errorState();
	}
	
	private void wrongInputFormat () {
		error("The format of your input is not valid");
	}
	
	
	//methods changing state:
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

	private void errorState() {
		setTempState(ScreenState.ErrorState);
	}
	
}
