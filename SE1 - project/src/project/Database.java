package project;

import java.util.ArrayList;
import java.util.List;

import Exceptions_Enums.WrongInputException;




public class Database {
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
	
	public Employee getEmployee (int ID) {
		for (Employee employee:employees) {
			if (employee.ID==ID) return employee;
		}
		return null;
	}
	
	protected boolean isProjectLeader (Employee employee) {
		for (Project project:projects) {
			if (project.isProjectLeader(employee)) return true;
		}
		return false;
	}
	
	protected boolean projectExcists (String name) {
		for (Project project:projects) {
			if (project.name==name) return true;
		}
		return false;
	}
	
	protected int getNextProjectNumber() {
		return projects.size();
	}
	
	protected boolean addProject (Project project) throws WrongInputException {
		if (project==null){
			throw new WrongInputException("Project is null");
		}
		this.projects.add(project);
		return true;
	}
	
	public Assignment getAssignment (int taskID, int employeeID) {
		for (Assignment assignment:assignments) {
			if (assignment.taskID==taskID && employeeID==assignment.employeeID) return assignment;
		}
		return null;
	}

	protected List<Assignment> getEmployeeAssignments (Employee employee) {
		List<Assignment> empAssignments=new ArrayList<Assignment>();
		for (Assignment assignment:assignments) {
			if (assignment.employeeID==employee.ID) empAssignments.add(assignment);
		}
		
		return empAssignments;
	}
	
	protected MyMap getEmployeeDayBookings(Employee employee, CalDay day) {
		//init of lists
		MyMap dayBookings= new MyMap();
		List<WorkPeriod> wpBookings = new ArrayList<WorkPeriod>();
		List<Assignment> associatedAssignments = new ArrayList<Assignment>();
		dayBookings.mainInfo=wpBookings;
		dayBookings.secondaryInfo=associatedAssignments;
		
		//Adds all todays bookings and associated assignments to the MyMap
		for(Assignment assignment: assignments){
			if (assignment.employeeID==employee.ID) {
				List<WorkPeriod> assPeriods=assignment.getDayBookings(day);
				//adds assignment workperiod for today to todays bookings
				for (WorkPeriod wp:assPeriods) {
					wpBookings.add(wp);
				}
				//adds associated assignment for assignments workperiods to asociated assignment
				for (int i=0;i<assPeriods.size();i++) {
					associatedAssignments.add(assignment);
				}
			}
		}
		return dayBookings;
	}

	protected boolean createBookingForCoWorker(Employee coWorker, int taskID, WorkPeriod period) throws WrongInputException{
		if(coWorker==null)throw new WrongInputException("Employee doesn't excist");
		Task task=getTask(taskID);
		if (task==null) throw new WrongInputException("Task doesn't excist");
		
		Assignment coWorkerNew = new Assignment(task, coWorker);		
		if (coWorkerNew.addBooking(period)) {
			assignments.add(coWorkerNew);
			return true;
		}
		return false;		
	}
	
	public Task getTask (int taskID) {
		for (Task task:tasks) {
			if (task.ID==taskID) return task;
		}
		return null;
	}
	
	public Project getProject (int projectID) {
		for (Project project:projects){
			if (project.ID==projectID) return project;
		}
		return null;
	}
	
	public Task addTask(Task task) throws WrongInputException{
		if (task==null) throw new WrongInputException("Task doesn't exist, and therefore was not created or added");
		task.ID=tasks.size();
		tasks.add(task);
		return task;
	}
	
	protected boolean taskExists (Project project, String name) {
		for (Task task:tasks) {
			if (task.name==name && task.projectID==project.ID){
				return true;
			}
		}
		return false;
	}
	
	protected Assignment createAssignment(Employee employee, Task task) throws WrongInputException {
		if (employee==null) throw new WrongInputException("Employee doesn't exist");
		if (task==null) throw new WrongInputException("Task doesn't exist");
		Assignment ass = new Assignment(task,employee);
		assignments.add(ass);
		return ass;	
	}
	
	
	
	
	
	
	
	
	
	
	
 	public boolean noEmployeeExcists() {
		if (employees.size()==0) return true;
		return false;
	}

	public List<String> getAvailableEmployees(CalWeek start, CalWeek end) {
		List<String> employeesAvailable= new ArrayList<String>();
		
		for (Employee employee:employees) {
			double availableTime=employee.hoursAvailable(this, start,end);
			if (availableTime>0) employeesAvailable.add(employee.name + " hours free: " + availableTime);
		}
		return employeesAvailable;
	}

	
	
	
	
}


