package project;

import java.util.ArrayList;
import java.util.List;

import Exceptions_Enums.WrongInputException;

/*
 * Authors: Benjamin, Christian, Asbj�rn, Martin
 * 
 */

public class Database {
	public List<Employee> employees;
	public List<Project> projects;
	public List<Task> tasks;
	public List<Assignment> assignments;
	int nextEmpID;
	int nextTaskID;
	int nextProjectID;
	
	public Database () {
		this.employees=new ArrayList<Employee>();
		this.projects=new ArrayList<Project>();
		this.tasks=new ArrayList<Task>();
		this.assignments=new ArrayList<Assignment>();
		
		this.nextEmpID=0;
		this.nextTaskID=0;
		this.nextProjectID=0;
	}
	
	public void initDatabase () throws WrongInputException {
		//creates the powerful secret project
		Project project = new Project("project0");
		addProject(project);
		
		//Sickness has taskID=0 and is under projectID=0
		Task sickness = new Task(project, "Sickness");
		addTask(sickness);
		
		//Vacation has taskID=1 and is under projectID=0
		Task vacation = new Task(project, "Vacation");
		addTask(vacation);		
		
		//Course has taskID=2 and is under projectID=0
		Task course = new Task(project, "Course");
		addTask(course);
	}

	public Employee getEmployee (int ID) {
		for (Employee employee:employees) {
			if (employee.ID==ID) return employee;
		}
		return null;
	}
	
	public boolean isProjectLeader (Employee employee) {
		for (Project project:projects) {
			if (project.isProjectLeader(employee)) return true;
		}
		return false;
	}
	
	protected boolean projectExcists (String name) {
		for (Project project:projects) {
			if (project.name.equals(name)) return true;
		}
		return false;
	}
	
	protected Project addProject (Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException("Project doesn't excist");
		project.ID=nextProjectID++;
		projects.add(project);
		return project;
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

	public boolean createBookingForCoWorker(Employee coWorker,Task task, WorkPeriod period) throws WrongInputException{
		if(coWorker==null)throw new WrongInputException("Employee doesn't excist");
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
		task.ID=nextTaskID++;
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
	
	protected List<Task> projectGetTasks(Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException ("Project doesn't excist");
		List<Task> projectTasks = new ArrayList<Task>();
		
		for (Task task:tasks) {
			if (task.projectID==project.ID) projectTasks.add(task);
		}
		return projectTasks;
	}
	
	protected List<Employee> getEmployeesForProject (Project project) throws WrongInputException {
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
	
	protected List<Employee> getEmployeesForTask (Task task) throws WrongInputException {
		if (task==null) throw new WrongInputException ("Task doesn't exist");
		
		List<Employee> taskEmployees = new ArrayList<Employee>();			
		for (Assignment assignment:assignments) {
			if (assignment.taskID==task.ID) {
				Employee tempEmp=getEmployee(assignment.employeeID);
				if (!taskEmployees.contains(tempEmp)) taskEmployees.add(tempEmp);
			}
		}
		return taskEmployees;
	}
	
	protected WorkPeriod removeBooking (Task task, Employee employee, CalDay calDay, double start, double end) throws WrongInputException {
		Assignment assignment=getAssignment(task.ID,employee.ID);
		if (assignment==null) throw new WrongInputException ("The selected employee is now working on the task");
		return assignment.removeBooking(calDay, start, end);	
	}
	
	protected Employee addEmployee (Employee employee) throws WrongInputException {
		if (employee==null) throw new WrongInputException("Employee doesn't excist");
		employee.ID=nextEmpID;
		nextEmpID++;
		employees.add(employee);
		//creates sickness,vacation and course
		for(int i=0;i<3;i++){
			createAssignment(employee, getTask(i));
		}
		return employee;
	}
	
	protected Employee removeEmployee (Employee employee) throws WrongInputException {
		if (employee==null) throw new WrongInputException ("Employee doesn't exist");
		if (!employees.remove(employee)){
			throw new WrongInputException ("Employee doesn't exist");
		}
		return employee;
	}
	
	protected Project removeProject (Project project) throws WrongInputException {
		if (project==null) throw new WrongInputException ("Project doesn't exist");
		if (!projects.remove(project)){
			throw new WrongInputException ("Project doesn't exist");
		}
		removeProjectTasks(project);
		return project;
	}
	
	protected void removeProjectTasks (Project project) throws WrongInputException {
		for (int i=tasks.size()-1;i>=0;i--){
			if (tasks.get(i).projectID==project.ID){
				removeTask(tasks.get(i));
			}
		}
	}
	
	protected Task removeTask (Task task) throws WrongInputException {
		if (task==null) throw new WrongInputException ("Task doesn't exist");
		if (!tasks.remove(task)){
			throw new WrongInputException ("Task doesn't exist");
		}
		removeTaskAssignments(task);
		return task;
	}
	
	protected void removeTaskAssignments(Task task) {
		for (int i=assignments.size()-1;i>=0;i--) {
			if (assignments.get(i).taskID==task.ID) {
				assignments.remove(i);
			}
		}
	}
	
	protected boolean noEmployeeExcists() {
		return employees.isEmpty();
	}
	
	public List<String> getAvailableEmployees(CalWeek start, CalWeek end) {
		List<String> employeesAvailable= new ArrayList<String>();
		
		for (Employee employee:employees) {
			double availableTime=employee.hoursAvailable(this, start,end);
			if (availableTime>0) employeesAvailable.add(employee.name + " hours free: " + availableTime);
		}
		return employeesAvailable;
	}
	
	
	// methods used only for testing:
	public int numberOfAssignments () {
		return this.assignments.size();
	}
	
	public int numberOfTasks() {
		return this.tasks.size();
	}

}


