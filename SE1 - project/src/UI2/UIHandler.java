package UI2;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import Enum_Interfaces_AbstractClasses.ScreenState;

import project.*;

//notify user fail or succes of operation

public class UIHandler extends Observable {
	public ScreenState currentState;
	public int subState;
	private Database database;
	private MainUI mainUI;
	private Employee employee;
	boolean isProjectLeader;
	
	public UIHandler (MainUI mainUI) {
		this.mainUI=mainUI;
		this.subState=0;
		this.database=new Database();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void init() {
		setState (ScreenState.LoginState);
	}
	
	public boolean handleInput(String userInput) {
		switch (currentState){
		case LoginState:
			return logIn(Integer.parseInt(userInput));
		case EmployeeState:
			return handleEmployeeState(userInput);
		case ProjectLeaderState:
			break;
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
	private boolean handleEmployeeState(String userInput) {
		boolean succesfullInput=false;
		boolean userChoosedFromMenu=false;
		
		switch (this.subState) {
		case 0: succesfullInput=selectEmployeeSubstate(userInput); 
				userChoosedFromMenu=true;
				break;	
		case 1: succesfullInput=registerWork(userInput); break;
		case 2: succesfullInput=seekAssistance(userInput); break;
		case 3: succesfullInput=registerVacation(userInput); break;
		case 4: succesfullInput=registerSickness(userInput); break;
		case 5: succesfullInput=registerCourse (userInput); break;
		case 6: succesfullInput=createNewProject (userInput); break;
		case 7: succesfullInput=setProjectLeader (userInput); break;
		case 8: succesfullInput=changeProject (); break;
		}
		if (!userChoosedFromMenu && succesfullInput) setSubState(0);
	
		return succesfullInput;
	}
	private boolean selectEmployeeSubstate (String userInput) {
		int userChoice=Integer.parseInt(userInput);
		if (userChoice<0 || userChoice>9) return false;
		if (!isProjectLeader && userChoice==8) return false;
		if (userChoice==9) logOff();
		setSubState(userChoice);
		return true;
	}
	private boolean registerWork(String userInput){
		return false;
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
	private boolean createNewProject(String userInput){
		String[] inputs=seperateString(userInput);
		if (inputs.length<2) return false;
		
		int ID=Integer.parseInt(inputs[1]);
		
		return createProject(inputs[0],ID);
	}
	private boolean setProjectLeader(String userInput){
		return false;
	}
	private boolean changeProject() {
		setState(ScreenState.ProjectLeaderState);
		return true;
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
	
	private boolean createProject (String name, int ID) {
			return database.createProject(name, ID);
	}

	private String[] seperateString (String input) {
		return input.split(" ");
	}
}
