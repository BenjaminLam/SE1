package project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Enum_Interfaces_AbstractClasses.ScreenState;
import Exceptions_Errors.WrongInputException;

//implementere hele moletjavsen med error i stedet for exception
//n�r en metode fejler udskriver den error og returnerer false, s� bruger kan pr�ve igen



public class SysApp extends Observable {
	public Employee currentEmp;
	public boolean isProjectLeader;
	private Database database;
	public Object projects;
	
	public SysApp () {
		this.database=new Database();
	}
	
	public Employee logIn(int EmpID) {
		Employee employee=database.getEmployee(EmpID);
		if (employee==null) return null;
		
		currentEmp=employee;
		isProjectLeader=employee.isProjectLeader(database);
		return employee;
	}
	
	public Project createProject (String name) throws WrongInputException {
		if (!database.projectExcists(name)) throw new WrongInputException("No project exists with that name.");
		Project project=new Project(name, database.getNextProjectNumber());
		database.addProject(project);
		changed(project);
		return project;
	}
	
	public WorkPeriod copyBookingToTimeRegister(WorkPeriod booking, Assignment assignment) throws WrongInputException{
		if (booking==null){
			throw new WrongInputException("The booking doesn't exist");
		}
		if(assignment==null){
			throw new WrongInputException("The assignment doesn't exist");
		}
		changed(booking);
		return assignment.addTimeRegister(booking);
	}

	public WorkPeriod registerWorkManually(int taskID, double start, double end, CalDay day) throws WrongInputException{
		Assignment tempAss=database.getAssignment(taskID,currentEmp.ID);
		if(tempAss==null)throw new WrongInputException("You do not work on this assignemt");
		WorkPeriod wp=new WorkPeriod(day,start,end);
		changed(wp);
		return tempAss.addTimeRegister(wp);
	}

	public Employee seekAssistance(int empID,int taskID,WorkPeriod period) throws WrongInputException{
		Employee coWorker=database.getEmployee(empID);
		if (coWorker==null) throw new WrongInputException("No employee exist with that ID");
		if(!coWorker.isAvailable(period, database)){
			throw new WrongInputException("Your co-worker is not available in this period.");
		}
		database.createBookingForCoWorker(coWorker,taskID,period);
		return coWorker;
	}
	
	public Employee setProjectLeader(int projectID, int employeeID) throws WrongInputException {
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
		changed(emp);
		return project.projectLeader;
	}
	
	//ovenst�ende er brugt af UI Employee state
	
	public boolean createTask (int projectID, String name) throws WrongInputException {
		return currentEmp.createTask(database, projectID, name);
	}
	
	public Task setTaskStart (int taskID, int year, int week) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null) throw new WrongInputException("Task doesn't exist");
		return task.setStart(new CalWeek (year,week), currentEmp, database);
	}
	
	public boolean setTaskEnd (int taskID, int year, int week) throws WrongInputException {
		Task task=database.getTask(taskID);
		if (task==null){
			throw new WrongInputException ("Wrong input: Task doesn't exist");
		};
		return task.setEnd(new CalWeek (year,week), currentEmp, database);
	}
	
	public List<String> employeesForTask(int taskID) {
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

	public boolean manTask (int taskID, int employeeID) {
		return currentEmp.manTask(database, taskID,employeeID);
	}
	
	public boolean setTaskBudgetTime(int taskID, double timeBudget) {
		Task task=database.getTask(taskID);
		if (task==null) return false;
		return currentEmp.setTaskBudgetTime(database, task,timeBudget);
	}
	
	//ovenst�ende er brugt af UI project leader state
	
	

	
	
	
	
	
	
	public MyMap getTodaysBookings() {
		return currentEmp.dayBookings(Util.getCurrentDay(),database);
	}
	
	//ovenst�ende er hj�lpemetoder brugt af UI
	
	//ikke bare flyttes til database?
	//used by seekAssistance
	
	
	
		
	
	
	
	
	
	
	//ikke tjekket endnu:
	
	
	
	public boolean createEmployee (String name, int ID) throws WrongInputException {
		if (employeeExcists(ID)) return false;
		Employee emp=new Employee(name,ID);
		employees.add(emp);
		changed(emp);
		return true;
	}

	
	
	
	
	
	
	private boolean employeeExcists(int ID) {
		if (getEmployee(ID)==null) return false;
		return true;
	}
	
	
	
	public int getNumberOfTasks() {
		return tasks.size();
	}
	
	public int getNumberOfAssignments(){
		return database.assignments.size();
	}
	
	
	
	public boolean assignmentExists(int taskID, int employeeID){
		for(Assignment assignment: assignments){
			if(taskID==assignment.taskID && employeeID==assignment.employeeID){
				return true;
			}
		}
		return false;
	}
	
	
	
	
		
	
	
	public void initDatabase () {
		//creates the powerful secret project
		Project project = new Project("project0",0);
		this.projects.add(project);
		
		Task sickness = new Task(project, "Sickness");
		this.tasks.add(sickness);
		
		Task vacation = new Task(project, "Vacation");
		this.tasks.add(vacation);
		
		Task course = new Task(project, "Course");
		this.tasks.add(course);
	}

	
	
// unused?	
//	public void createBooking(CalDay day, double start, double end,Assignment assignment) throws WrongInputException{
//		WorkPeriod newBooking = new WorkPeriod(day,start,end);
//		assignment.bookings.add(newBooking);
//	}
	
	
	//not handling null input
	
	
	public boolean addAssignment(Assignment assignment){
		if(assignment==null){
			return false;
		}
		assignments.add(assignment);
		return true;
	}
	
	
	
	
	
	public List<Employee> projectEmployees (Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		
		List<Employee> projectEmployees = new ArrayList<Employee>();
		
		List<Task> projectTasks=projectGetTasks(project);
		
		for (Task task:projectTasks) {
			for (Assignment assignment:assignments) {
				if (assignment.taskID==task.ID) {
					Employee tempEmp=getEmployee(assignment.employeeID);
					if (!projectEmployees.contains(tempEmp)) projectEmployees.add(tempEmp);
				}
			}
		}

		return projectEmployees;
	}
	
	public double projectHoursSpent(Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException("project doesn't excist");
		double hoursSpent=0;
	
		List<Task> projectTasks=projectGetTasks(project);
		
		for (Task task:projectTasks) {
			for (Assignment assignment:assignments) {
				if (assignment.taskID==task.ID) hoursSpent+=assignment.getCumulativeTimeRegisters();
			}
		}
		
		return hoursSpent;
	}

	public double projectHoursProjected (Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		double hoursProjected=0;
		
		for (Task task:tasks) {
			if (task.projectID==project.ID) hoursProjected+=task.timeBudget;
		}
		
		return hoursProjected;
	}

	public List<Task> projectCompletedTasks (Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException("Project doesn't excist");
		List<Task> completedTasks=new ArrayList<Task>();
		List<Task> projectTasks=projectGetTasks(project);
		
		for (Task task:projectTasks) {
			try {
				if(task.inPast()) completedTasks.add(task);
			} catch (WrongInputException e) {
				
			}
		}
		
		return completedTasks;
	}

	public List<Task> projectRemainingTasks (Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException("Project doesn't excist");
		List<Task> projectTasks=projectGetTasks(project);
		List<Task> completedTasks=projectCompletedTasks(project);
		List<Task> remainingTasks=new ArrayList<Task>();
		
		for (Task task:projectTasks) {
			if (!completedTasks.contains(task)) remainingTasks.add(task); 
		}
		
		return remainingTasks;
	}

	private List<Task> projectGetTasks(Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		List<Task> projectTasks = new ArrayList<Task>();
		
		for (Task task:tasks) {
			if (task.projectID==project.ID) projectTasks.add(task);
		}
		return projectTasks;
	}
	
	public List<String> getProjectReport(Project project, Employee projectEmployee) throws WrongInputException {
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		if (!project.isProjectLeader(projectEmployee)) throw new WrongInputException ("You are not the project leader of this project");
		
		List<String> projectReport=new ArrayList<String>();
		
		projectReport.add(project.name);
		
		List<Employee> projectEmployees=projectEmployees(project);
		
		projectReport.add("" + projectEmployees.size());
		for (Employee employee:projectEmployees) {
			projectReport.add(employee.ID + " " + employee.name);
		}
		
		projectReport.add("" + projectHoursProjected(project));
		
		projectReport.add("" + projectHoursSpent(project));
		
		List<Task> remainingTasks=projectRemainingTasks(project);
		
		for (Task task:remainingTasks) {
			projectReport.add(task.name);
		}
		
		projectReport.add("*"); //asteriks to seperate remaining tasks from completed tasks
		
		List<Task> completedTasks=projectCompletedTasks(project);
		
		for (Task task:completedTasks) {
			projectReport.add(task.name);
		}
		
		return projectReport;
	}

	

	public void changed(Object o) {
		if (o==null) return;
		this.setChanged();
		this.notifyObservers(o);
	}

	
	//hj�lpemetoder
	
	
	
	
	
}
