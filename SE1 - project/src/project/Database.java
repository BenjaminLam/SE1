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

public class Database extends Observable {
	public Employee currentEmp;
	public boolean isProjectLeader;
	public List<Employee> employees;
	public List<Project> projects;
	private List<Task> tasks;
	public List<Assignment> assignments;
	
	public Database () {
		this.employees=new ArrayList<Employee>();
		this.projects=new ArrayList<Project>();
		this.tasks=new ArrayList<Task>();
		this.assignments=new ArrayList<Assignment>();
	}
	
	public boolean logIn(int EmpID) {
		Employee employee=getEmployee(EmpID);
		//if (employee==null) return false;
		currentEmp=employee;
		isProjectLeader=isProjectLeader(employee);
		return true;
	}
	
	public boolean createProject (String name) throws WrongInputException {
		if (projectExcists(name)) return false;
		Project project=new Project(name, projects.size());
		projects.add(project);
		changed(project);
		
		return true;
	}
	

	
	//ovenstående er brugt af UI
	
	
	
	public boolean projectExcists (String name) {
		for (Project project:projects) {
			if (project.name==name) return true;
		}
		return false;
	}
	
	public boolean createEmployee (String name, int ID) throws WrongInputException {
		if (employeeExcists(ID)) return false;
		Employee emp=new Employee(name,ID);
		employees.add(emp);
		changed(emp);
		return true;
	}

	public Assignment getAssignment (int taskID, Employee employee) {
		for (Assignment assignment:assignments) {
			if (assignment.taskID==taskID && employee.equals(employee)) return assignment;
		}
		return null;
	}
	
	public Task getTask (int taskID) {
		return tasks.get(taskID);
	}
	
	public Employee getEmployee (int ID) {
		for (Employee employee:employees) {
			if (employee.ID==ID) return employee;
		}
		return null;
	}
	
	private boolean employeeExcists(int ID) {
		if (getEmployee(ID)==null) return false;
		return true;
	}
	
	public Project getProject (int projectID) {
		for (Project project:projects){
			if (project.ID==projectID) return project;
		}
		return null;
	}
	
	public int getNumberOfTasks() {
		return tasks.size();
	}
	
	public int getNumberOfAssignments(){
		return assignments.size();
	}
	
	public boolean taskExists (int projectID, String name) {
		for (Task task:tasks) {
			if (task.name==name && task.projectID==projectID){
				return true;
			}
		}
		return false;
	}
	
	public boolean assignmentExists(int taskID, int employeeID){
		for(Assignment assignment: assignments){
			if(taskID==assignment.taskID && employeeID==assignment.employeeID){
				return true;
			}
		}
		return false;
	}
		
	public List<Assignment> getEmployeeAssignments (Employee employee) {
		List<Assignment> empAssignments=new ArrayList<Assignment>();
		for (Assignment assignment:assignments) {
			if (assignment.employeeID==employee.ID) empAssignments.add(assignment);
		}
		
		return empAssignments;
	}
	
	public MyMap employeesTodaysBookings(Employee employee, CalDay day){
		//init of lists
		MyMap todaysBookings= new MyMap();
		List<WorkPeriod> wpBookings = new ArrayList<WorkPeriod>();
		List<Assignment> associatedAssignments = new ArrayList<Assignment>();
		todaysBookings.mainInfo=wpBookings;
		todaysBookings.secondaryInfo=associatedAssignments;
		
		List<Assignment> empAssignments = getEmployeeAssignments(employee);
		
		//Adds all todays bookings and associated assignments to the MyMap
		for(Assignment assignment: empAssignments){
			for(WorkPeriod w:assignment.bookings){
				if(w.day.equals(day)) {
					wpBookings.add(w);
					associatedAssignments.add(assignment);
				}
			}
		}
		return todaysBookings;
	}
	
	public void copyBookingToTimeRegister(WorkPeriod booking, Assignment assignment){
		assignment.timeRegisters.add(booking);
	}
	
	public void registerWorkManually(int taskID, int start, int end, CalDay day){
		if(day==null){
			day=getCurrentDay();
		}
		Assignment TempAss=getAssignment(taskID,currentEmp);
		TempAss.timeRegisters.add(new WorkPeriod(day,start,end));
	}
	
	public void seekAssistance(WorkPeriod period,Employee coWorker,Assignment assignment) throws WrongInputException{
		if(checkIfCoWorkerIsAvailable(period,coWorker)){
			createBookingForCoWorker(period,coWorker,assignment);
		}
	}
	
	public boolean checkIfCoWorkerIsAvailable(WorkPeriod period,Employee coWorker){
		if(coWorker==null){
			return false;
		}
		for(Object booking: employeesTodaysBookings(coWorker,period.day).mainInfo){
			booking=(WorkPeriod) booking;
			
			if(booking.equals(period)){
				return false;
			}
		}
		return true;
	}

	public void createBooking(CalDay day, int start, int end,Assignment assignment){
		WorkPeriod newBooking = new WorkPeriod(day,start,end);
		addBookingToAssignment(newBooking,assignment);
	}
	
	public void addBookingToAssignment(WorkPeriod period,Assignment assignment){
		assignment.bookings.add(period);
	}
	
	public void createBookingForCoWorker(WorkPeriod period,Employee coWorker, Assignment assignment) throws WrongInputException{
		if(coWorker==null)throw new WrongInputException("Employee doen't excist");
		Assignment coWorkerNew = new Assignment(getTask(assignment.taskID), coWorker);
		addBookingToAssignment(period,coWorkerNew);
		assignments.add(coWorkerNew);
	}
	
	//not handling null input
	public int addTask(Task task){
		task.ID=tasks.size();
		tasks.add(task);
		return task.ID;
	}
	public boolean addAssigment(Assignment assignment){
		if(assignment==null){
			return false;
		}
		assignments.add(assignment);
		return true;
	}
	
	public double hoursBooked (Employee employee, CalWeek start, CalWeek end) {
		double hoursBooked=0;
		for (Assignment assignment: assignments) {
			if (assignment.employeeID==employee.ID) {
				for (WorkPeriod wp:assignment.bookings) {
					if (wp.day.week.isAfter(start) && wp.day.week.isBefore(end)) {
						hoursBooked+=wp.getLength();
					}
				}
			}
		}
		return hoursBooked;
	}
	
	public double hoursAvailable (Employee employee, CalWeek start, CalWeek end) {
		double availableTime=(end.week-start.week+1)*37.5;
		
		return availableTime-hoursBooked(employee,start,end);
	}
	
	public List<String> getAvailableEmployees (Employee mainEmployee, Task task) throws WrongInputException {
		if (task==null) throw new WrongInputException("Task doesn't excist");
		
		Project project=getProject(task.projectID);
		if (!project.isProjectLeader(mainEmployee)) throw new WrongInputException("You are not the project leader for the project of the task");
		
		if (task.start==null)throw new WrongInputException("Task doesn't have a start date");
		
		if (task.end==null)throw new WrongInputException("Task doesn't have an end date");
		
		List<String> employeesAvailable= new ArrayList<String>();
		
		for (Employee employee:employees) {
			double availableTime=hoursAvailable(employee,task.start,task.end);
			if (availableTime>0) employeesAvailable.add(employee.name + " hours free: " + availableTime);
		}
		
		return employeesAvailable;
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

	
	//hjælpemetoder
	public CalDay getCurrentDay () {
		Calendar calendar=new GregorianCalendar();
		int year=calendar.get(Calendar.YEAR);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		int day=calendar.get(Calendar.DAY_OF_WEEK);
		
		return new CalDay(new CalWeek(year,week),day);
	}
	
	
	private boolean isProjectLeader (Employee employee) {
		for (Project project:projects) {
			if (employee.equals(project.projectLeader)) return true;
		}
		return false;
	}
	
}
