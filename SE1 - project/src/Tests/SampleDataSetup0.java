package Tests;

import java.util.ArrayList;
import org.junit.Before;

import project.Database;
import project.Employee;
import project.Project;

public class SampleDataSetup0 {
	protected Database database=new Database();
	
	@Before
	public void setup() {
		for (int i=0;i<10;i++){
			database.employees.add(new Employee("Benjamin" + i,i));
			database.projects.add(new Project ("Project" + i,i));
		}
		database.projects.get(0).projectLeader=database.employees.get(0);
	}	
}
