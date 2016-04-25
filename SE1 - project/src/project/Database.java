package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
	public List<Employee> employees = new ArrayList<Employee>();
	public List<Project> projects = new ArrayList<Project>();
	public List<Task> tasks = new ArrayList<Task>();
	public List<Assignment> assignments=new ArrayList<Assignment>();
	
	public Database () {
	}
	
	public Task getTask (int taskID) {
		for (Task task:tasks){
			if (task.ID==taskID) return task;
		}
		return null;
	}
}
