package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
	public List<Employee> employees = new ArrayList<Employee>();
	public List<Project> projects = new ArrayList<Project>();
	public List<Task> tasks = new ArrayList<Task>();
	
	public Database () {
	}
	
	public boolean isProjectLeader(Employee employee){
		for (Project project:projects){
			if (project.projectLeader!= null && project.projectLeader.equals(employee)) return true;
		}
		return false;
	}
	
	public List<Project> leaderProjects (Employee employee) {
		List<Project> leaderProjects = new ArrayList<Project>();
		
		for (Project project:projects) {
			if (project.projectLeader.equals(employee)) leaderProjects.add(project);
		}
		
		return leaderProjects;
	}
}
