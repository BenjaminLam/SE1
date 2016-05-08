package Tests;

import org.junit.Before;

import Exceptions_Enums.WrongInputException;
import project.Assignment;
import project.CalDay;
import project.CalWeek;
import project.Database;
import project.Employee;
import project.Project;
import project.SysApp;
import project.Task;
import project.WorkPeriod;

public class SampleDataSetupTest {
	protected Database database=new Database();
	protected SysApp sysApp=new SysApp(database);
	/*
	 * Creates 10 employees, Employee0..Employee9
	 * Creates 10 projects, Project1..Project10
	 * Employee0 is project leader of project0
	 * project0 is a special project which contains tasks for sickness, vacation and courses
	 * 
	 * Creates 10 tasks, Task0..Task9. 
	 * Task[i] is belonging to Project[i]
	 * 
	 * creates 10 assignments
	 * Assignment[i] is connected to task[i] and Employee[i]
	 * 
	 * creates 10 workperiod(bookings)
	 * workperiod[i] belongs to assignment[i]
	 * workperiod[i] starts at 9.00 and lasts til 9.00+i
	 * 
	 * Adds 6 workperiods to employee9, each lasting 24 hours
	 */
	@Before
	public void setup() throws WrongInputException {
		sysApp.init();
		CalDay day=new CalDay(new CalWeek(2000,2),1);
		for (int i=0;i<10;i++){
			sysApp.createEmployee("Employee"+i);
			Employee tempEmp=database.getEmployee(i);
			sysApp.createProject("project"+(i+1));
			Project tempPro=database.getProject(i+1);
			Task tempTask=new Task(tempPro,"Task" + i);
			database.addTask(tempTask);
			Assignment tempAss=new Assignment(tempTask,tempEmp);
			database.assignments.add(tempAss);
			WorkPeriod tempWP=new WorkPeriod (day,9,9+i);
			tempAss.bookings.add(tempWP);
			
			if(i==9) {
				for (int j=0;j<6;j++) {
					tempAss.bookings.add(new WorkPeriod(day,0,24));
				}
			}
		}
		database.projects.get(1).projectLeader=database.employees.get(0);
		
	}	
}
