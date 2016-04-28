package Tests;

import java.util.ArrayList;
import org.junit.Before;

import project.*;

public class SampleDataSetup0 {
	protected Database database=new Database();
	/*
	 * Creates 10 employees, Employee0..Employee9
	 * Creates 10 projects, Project0..Project9
	 * Employee0 is project leader of project0
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
	public void setup() {
		CalDay day=new CalDay(new CalWeek(2000,2),1);
		for (int i=0;i<10;i++){
			Employee tempEmp=new Employee("Employee" + i,i);
			database.employees.add(tempEmp);
			Project tempPro=new Project ("Project" + i,i);
			database.projects.add(tempPro);
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
		database.projects.get(0).projectLeader=database.employees.get(0);
		
	}	
}
