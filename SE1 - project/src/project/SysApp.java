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
	public String[] copyBookingToTimeRegister(WorkPeriod booking, Assignment assignment) throws WrongInputException{
		if (booking==null){
			throw new WrongInputException("The booking doesn't exist");
		}
		if(assignment==null){
			throw new WrongInputException("The assignment doesn't exist");
		}
		assignment.addTimeRegister(booking);
		return new String[]{
				"Added time register succesfully to database",
		};
	}

	public String[] registerWorkManually(int taskID, double start, double end, CalDay day) throws WrongInputException{
		Assignment tempAss=database.getAssignment(taskID,currentEmp.ID);
		if(tempAss==null)throw new WrongInputException("You do not work on this assignemt");
		WorkPeriod wp=new WorkPeriod(day,start,end);
		tempAss.addTimeRegister(wp);
		return new String[]{
			"Registered work succesfully from " + start + " to " + end + "for task: " + database.getTask(taskID).name	
		};
	}

	public String[] seekAssistance(int empID,int taskID,WorkPeriod period) throws WrongInputException{
		Employee coWorker=database.getEmployee(empID);
		if (coWorker==null) throw new WrongInputException("No employee exist with that ID");
		if(!coWorker.isAvailable(period, database)){
			throw new WrongInputException("Your co-worker is not available in this period.");
		}
		Task task=database.getTask(taskID);
		database.createBookingForCoWorker(coWorker,task,period);
		
		return new String[]{
				"You succesfully seeked assistance by " + coWorker.name,
				"With task " + task.ID,
				"From " + period.start + " to " + period.end
		};
	}
	
	public void registerSickness () throws WrongInputException {
		CalDay today = Util.getCurrentDay();
		currentEmp.setSickness(database);
	}
	
	public void registerVacation () {
		
	}
	
	public void registerCourse () {
		
	}
	
	public String[] createProject (String name) throws WrongInputException {
		if (database.projectExcists(name)) throw new WrongInputException("A project with that name already exists");
		Project project=new Project(name);
		database.addProject(project);
		return new String[]{
			"You succesfully created a new project with project name " + project.name + " and project id " + project.ID	
		};
	}
	
	public String[] setProjectLeader(int projectID, int employeeID) throws WrongInputException {
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
		return new String[] {
			"Succesfully assigned " + emp.name	+ " as project leader for " + project.name
		};
	}
	
	public String[] createEmployee (String name) throws WrongInputException {
		Employee employee=new Employee (name);
		database.addEmployee(employee);
		return new String[] {
				"Succesfully created employee " + employee.name + "with employee ID " + employee.ID
		};
	}
	
	public String[] removeEmployee (int empID) throws WrongInputException {
		Employee employee=database.getEmployee(empID);
		if (employee.equals(currentEmp)) throw new WrongInputException("You can't remove yourself from the database");
		database.removeEmployee(employee);
		return new String[] {
			"Succesfully remove employee " + employee.name + " with earlier employee ID: " + employee.ID	
		};
	}
	

	//nedenstående er brugt at UI project leader state
	
	public String[] renameProject(int projectID, String name) throws WrongInputException {
		Project project=database.getProject(projectID);
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		if (database.projectExcists(name)) throw new WrongInputException ("Project with that name already excists");
		project.name=name;
		return new String[]{
			"Succesfully renamed project with project id " + project.ID + " to: " + project.name	
		};
	}
	
	public void setProjectStart() {
		
	}
	
	public void setProjectEnd() {
		
	}
	
	public String[] removeProject (int projectID) throws WrongInputException {
		Project project = database.getProject(projectID);
		if(project == null) throw new WrongInputException ("Project doesn't exist");
		if(!project.projectLeader.equals(currentEmp)) throw new WrongInputException("You are not the project leader of this project");
		database.removeProject(database.getProject(projectID));
		return new String[]{
			"Succesfully removed project " + project.name	
		};
	}
	
	public String[] createTask (int projectID, String name) throws WrongInputException {
		Task task=currentEmp.createTask(database, projectID, name);
		return new String[]{
			"Succesfully created task "	+ task.name + " with task id: " + task.ID
		};
	}
	
	public String[] setTaskBudgetTime(int taskID, double timeBudget) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException("Task doesn't exist.");
		currentEmp.setTaskBudgetTime(database, task,timeBudget);
		return new String[]{
			"Succesfully set budget time for task " + task.name + " to " + task.timeBudget	
		};
	}
	
	public String[] setTaskStart (int taskID, int year, int week) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException("Task doesn't exist");
		task.setStart(new CalWeek (year,week), currentEmp, database);
		return new String[] {
			"Succesfully set task start for " + task.name + " to year " + year + " and week " + week 	
		};
	}
	
	public String[] setTaskEnd (int taskID, int year, int week) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null){
			throw new WrongInputException ("Wrong input: Task doesn't exist");
		}
		task.setEnd(new CalWeek (year,week), currentEmp, database);
		return new String[] {
			"Succesfully set task end for  " + task.name + " to year " + year + " and week " + week 	
		};
	}
	
	public String[] removeTask (int taskID) throws WrongInputException {
		database.removeTask(database.getTask(taskID));
		return new String[]{
				
		};
	}

	public String[] employeesForTask(int taskID) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException ("Task doesn't excist");
		
		List<String> availableEmps=task.getAvailableEmployees(currentEmp, database);
		if (availableEmps.isEmpty()) {
			return new String[] {
				"No employees are avilable"	
			};
		}
		
		availableEmps.add(0, "Following employees are available for the task: ");
		
		return (String[]) availableEmps.toArray();
	}

	public String[] renameTask(int taskID, String name) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException ("Task doesn't excist");
		task.rename(database, currentEmp, name);
		
		return new String[]{
			"Succesfully changed name of task with task id " + task.ID + " to " + task.name
		};
	}
	
	public String[] manTask (int taskID, int employeeID) throws WrongInputException {
		currentEmp.manTask(database, taskID,employeeID);
		return new String[] {
			"Succesfully attached " + database.getEmployee(employeeID).name + " to " + database.getTask(taskID).name	
		};
	}
	
	public void createBooking () {
		
	}
	
	public void removeBooking () {
		
	}
	
	public String[] createProjectReport(int projectID) throws WrongInputException {
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
		
		return (String[]) projectReport.toArray();
	}

	public String[] createTaskReport(int taskID) throws WrongInputException {
		Task task = database.getTask(taskID);
		if(task==null) throw new WrongInputException("Task doens't exist");
		if(!database.getProject(task.projectID).isProjectLeader(currentEmp)) throw new WrongInputException("You are not the project leader of this project");
		List<String> taskReport = new ArrayList<String>();
		
		taskReport.add("Task report for: " + task.name + " with taskID "+task.ID+".");
		
		List<Employee> taskEmployees=database.getEmployeesForTask(task);
		
		taskReport.add("This task has" + taskEmployees.size() + " employees:");
		for(Employee employee:taskEmployees){
			taskReport.add("ID: " + employee.ID+ " Name: " + employee.name);
		}
		
		taskReport.add("Time budget for this task:" + task.timeBudget);
		
		taskReport.add("Total time spent on this project: " + task.hoursSpent(database));
		return (String[]) taskReport.toArray();
	}
	
	
	//help method for register work in uihandler
	public MyMap todaysBookings() {
		return currentEmp.dayBookings(Util.getCurrentDay(),database);
	}
	
	
	//Er under process:
	
	public Employee setSickness (Database database, int employeeID) throws WrongInputException{
		Employee employee=database.getEmployee(employeeID);
		if (employee==null) throw new WrongInputException("Employee doesn't exist.");
		return currentEmp.setSickness(database);
	}

	public boolean isSick (Database database, int employeeID) throws WrongInputException{
		Employee employee=database.getEmployee(employeeID);
		if (employee==null) throw new WrongInputException("Employee doesn't exist.");
		return currentEmp.isSick(database, employee);
	}
	
	
	
	
	
	
	

}
