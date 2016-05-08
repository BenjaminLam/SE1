package project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Exceptions_Enums.*;

//unimplemented changed

public class SysApp {
	public Employee currentEmp;
	public boolean isProjectLeader;
	private Database database;
	
	public SysApp () {
		this.database=new Database();
	}
	
	public SysApp(Database database) {
		this.database=database;
	}
	
	public void init() throws WrongInputException{
		database.initDatabase();
	}
	
	public Employee logIn(int EmpID) throws WrongInputException {
		Employee employee=database.getEmployee(EmpID);
		//if (employee==null) throw new WrongInputException ("Employee doesn't excist");
		currentEmp=employee;
		//isProjectLeader=employee.isProjectLeader(database);
		return employee;
	}
	
	
	//nedenstående er brugt af UI Employee state
	public WorkPeriod copyBookingToTimeRegister(WorkPeriod booking, Assignment assignment) throws WrongInputException{
		if (booking==null){
			throw new WrongInputException("The booking doesn't exist");
		}
		if(assignment==null){
			throw new WrongInputException("The assignment doesn't exist");
		}
		return assignment.addTimeRegister(booking);
	}

	public WorkPeriod registerWorkManually(int taskID, double start, double end, CalDay day) throws WrongInputException{
		Assignment tempAss=database.getAssignment(taskID,currentEmp.ID);
		if(tempAss==null)throw new WrongInputException("You do not work on this assignemt");
		WorkPeriod wp=new WorkPeriod(day,start,end);
		return tempAss.addTimeRegister(wp);
	}

	public Object[] seekAssistance(int empID,int taskID,WorkPeriod period) throws WrongInputException{
		Employee coWorker=database.getEmployee(empID);
		if (coWorker==null) throw new WrongInputException("No employee exist with that ID");
		if(!coWorker.isAvailable(period, database)){
			throw new WrongInputException("Your co-worker is not available in this period.");
		}
		Task task=database.getTask(taskID);
		database.createBookingForCoWorker(coWorker,task,period);
		
		return new Object[]{coWorker,task,period};
	}
	
	public void registerSickness () {
		
	}
	
	public void registerVacation () {
		
	}
	
	public void registerCourse () {
		
	}
	
	public Project createProject (String name) throws WrongInputException {
		if (database.projectExcists(name)) throw new WrongInputException("A project with that name already exists");
		Project project=new Project(name);
		database.addProject(project);
		return project;
	}
	
	public Object[] setProjectLeader(int projectID, int employeeID) throws WrongInputException {
		Project project=database.getProject(projectID);
		if (project==null) throw new WrongInputException("There exist no project with this projectID");
		Employee emp=database.getEmployee(employeeID);
		if (emp==null) throw new WrongInputException("No employee exists with this employeeID");
		if (!(project.projectLeader==null)) {
			if (!project.projectLeader.equals(currentEmp)) {
				throw new WrongInputException("Project already has a project leader and it's not you.");
			}
		}
		project.projectLeader=emp;
		return new Object[]{project,emp};
	}
	
	public Employee createEmployee (String name) throws WrongInputException {
		Employee employee=new Employee (name);
		database.addEmployee(employee);
		return employee;
	}
	
	public Employee removeEmployee (int empID) throws WrongInputException {
		Employee employee=database.getEmployee(empID);
		if (employee.equals(currentEmp)) throw new WrongInputException("You can't remove yourself from the database");
		return database.removeEmployee(employee);
	}
	

	//nedenstående er brugt at UI project leader state
	
	public Project renameProject(int projectID, String name) throws WrongInputException {
		Project project=database.getProject(projectID);
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		if (database.projectExcists(name)) throw new WrongInputException ("Project with that name already excists");
		project.name=name;
		return project;
	}
	
	public void setProjectStart() {
		
	}
	
	public void setProjectEnd() {
		
	}
	
	public Project removeProject (int projectID) throws WrongInputException {
		try {
			if (!database.getProject(projectID).projectLeader.equals(currentEmp)) throw new WrongInputException("You are not the project leader of this project");
		} catch (NullPointerException e) {
			throw new WrongInputException ("Project doesn't excist");
		}
		return database.removeProject(database.getProject(projectID));
	}
	
	public Task createTask (int projectID, String name) throws WrongInputException {
		return currentEmp.createTask(database, projectID, name);
	}
	
	public Task setTaskBudgetTime(int taskID, double timeBudget) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException("Task doesn't exist.");
		return currentEmp.setTaskBudgetTime(database, task,timeBudget);
	}
	
	public Task setTaskStart (int taskID, int year, int week) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException("Task doesn't exist");
		return task.setStart(new CalWeek (year,week), currentEmp, database);
	}
	
	public Task setTaskEnd (int taskID, int year, int week) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null){
			throw new WrongInputException ("Wrong input: Task doesn't exist");
		};
		return task.setEnd(new CalWeek (year,week), currentEmp, database);
	}
	
	public Task removeTask (int taskID) throws WrongInputException {
		return database.removeTask(database.getTask(taskID));
	}

	public List<String> employeesForTask(int taskID) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException ("Task doesn't excist");
		
		List<String> availableEmps=task.getAvailableEmployees(currentEmp, database);
		if (availableEmps.isEmpty()) {
			List<String> noEmps=new ArrayList<String>();
			noEmps.add("No employees are avilable");
			return noEmps;
		}
		return availableEmps;
	}

	public void renameTask() {
		
	}
	
	public Assignment manTask (int taskID, int employeeID) throws WrongInputException {
		return currentEmp.manTask(database, taskID,employeeID);
	}
	
	public void createBooking () {
		
	}
	
	public void removeBooking () {
		
	}
	
	public List<String> createProjectReport(int projectID) throws WrongInputException {
		Project project=database.getProject(projectID);
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		if (!project.isProjectLeader(currentEmp)) throw new WrongInputException ("You are not the project leader of this project");
		
		List<String> projectReport=new ArrayList<String>();
		
		projectReport.add("Project report for: " + project.name + " with project ID " + project.ID+".");
		
		List<Employee> projectEmployees=database.getEmployeesForProject(project);
		
		projectReport.add("This project has " + projectEmployees.size() + " employees:");
		for (Employee employee:projectEmployees) {
			projectReport.add("ID: "+employee.ID + " Name: " + employee.name);
		}
		
		projectReport.add("Total hours projected for this project: " + project.hoursProjected(database));
		
		projectReport.add("Total hours spent for this project: " + project.hoursSpent(database));
		
		List<Task> remainingTasks=project.remainingTasks(database);
		
		projectReport.add("Remaning tasks: ");
		for (Task task:remainingTasks) {
			projectReport.add(task.name);
		}
		
		projectReport.add("Completed tasks: ");
		
		List<Task> completedTasks=project.completedTasks(database);
		
		for (Task task:completedTasks) {
			projectReport.add(task.name);
		}
		
		return projectReport;
	}

	public List<String> createTaskReport(int taskID) throws WrongInputException {
		Task task = database.getTask(taskID);
		if(task==null) throw new WrongInputException("Task doens't exist");
		if(!database.getProject(task.projectID).isProjectLeader(currentEmp)) throw new WrongInputException("You are not the project leader of this project");
		List<String> taskReport = new ArrayList<String>();
		
		taskReport.add("Task report for: " + task.name + " with taskID "+task.ID+".");
		
		List<Employee> taskEmployees=database.getEmployeesForTask(task);
		
		taskReport.add("This task has)
		for(Employee employee:taskEmployees){
			
		}
		
		return taskReport;
	}
	
	
	//help method for register work in uihandler
	public MyMap todaysBookings() {
		return currentEmp.dayBookings(Util.getCurrentDay(),database);
	}
	
	
	//Er under process:
	
	public Employee setSickness (Database database, int employeeID) throws WrongInputException{
		Employee employee=database.getEmployee(employeeID);
		if (employee==null) throw new WrongInputException("Employee doesn't exist.");
		return currentEmp.setSickness(database, employee);
	}

	public boolean isSick (Database database, int employeeID) throws WrongInputException{
		Employee employee=database.getEmployee(employeeID);
		if (employee==null) throw new WrongInputException("Employee doesn't exist.");
		return currentEmp.isSick(database, employee);
	}
	
	
}
