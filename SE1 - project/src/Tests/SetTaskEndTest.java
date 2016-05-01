package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Assert;

import org.junit.Test;

import Exceptions_Errors.*;
import project.*;
/* Tests for setTaskend
 * Author Asbjørn
 */
public class SetTaskEndTest extends SampleDataSetup0 {
	
	public void setTaskEndMain(){
		//Initialises objects
		Employee employee=super.database.employees.get(0);
		Project project=super.database.projects.get(0);
		Task task1 = database.getTask(1);
		CalWeek calWeek1 = new CalWeek(2000,5);
		//Checks if Employee is projectleader
		assertTrue(project.isProjectLeader(employee));
		
		task1.setTaskEnd(calWeek1, employee);
		assertNotNull(task1.end);
	}
	
}
