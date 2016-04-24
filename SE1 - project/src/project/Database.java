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
	
	public int userChooseFromList (List<Object> objects) {
		if (objects.size()<1) throw new IllegalArgumentException ("User has nothing to choose from");
		System.out.println("Options:");
		for (int i=0;i<objects.size();i++) {
			System.out.println("" + i+1 + ": " + objects.get(i).toString());
		}
		
		return userChooseNumber(1,objects.size());
	}
	
	private int userChooseNumber (int low, int high) {
		Scanner scanner=new Scanner(System.in);
		int userChoice=-1;
		scanner.nextInt();
		
		while (userChoice<low || userChoice>high) {
			System.out.println("You inputted a wrong Number. Try Again");
			scanner.nextInt();
		}
		
		return userChoice;
	}
}
