package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Exceptions_Errors.WrongInputException;

public class Task {
	public String name;
	public int ID;
	public int projectID;
	public double timeBudget;
	public CalWeek start;
	public CalWeek end;

	public Task(Project project, String name) {
		this.name = name;
		this.projectID = project.ID;
	}

	public boolean inPast() throws WrongInputException {
		if (end == null)
			throw new WrongInputException("Week doesn't excist");
		return end.isBefore(getCurrentWeek());
	}

	private CalWeek getCurrentWeek() {
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);

		return new CalWeek(year, week);
	}

	public void setTaskStart(CalWeek start, Employee employee, Database database) throws WrongInputException {
		CalWeek currentWeek = getCurrentWeek();
		Project project1 = database.getProject(0);
		if (!project1.isProjectLeader(employee)) {
			throw new WrongInputException("You are not projectleader of this project");
		}
		if (project1.start != null) {
			if (start.isBefore(project1.start)) {
				throw new WrongInputException("Selected date is before start date of project");
			}
		}
		if (start.isBefore(currentWeek)) {
			throw new WrongInputException("Invalid date; Date is in the past");
		}
		if (this.end != null) {
			if (start.isAfter(this.end)) {
				throw new WrongInputException("Invalid date; Date is after task end date");
			}
		}
		if (project1.end != null){
			if (start.isAfter(project1.end)){
				throw new WrongInputException("Invalid date; Date is after project end date");
			}
		}
		this.start = start;
		
	}
	
	public void setTaskEnd(CalWeek End, Employee employee, Database database) throws WrongInputException {
		CalWeek currentWeek = getCurrentWeek();
		Project project = database.getProject(0);
		if (!project.isProjectLeader(employee)){
			throw new WrongInputException("You are not the projectleader of this project");
		}
		if(project.start != null){
			if(End.isBefore(project.start)){
				throw new WrongInputException("Invalid date; Date is before project start");
			}
		}
		if(project.end != null){
			if (End.isAfter(project.end)){
				throw new WrongInputException("Invalid date; date is after project end");
			}
		}
		if (End.isBefore(currentWeek)) {
			throw new WrongInputException("Invalid date; Date is in the past");
		}
		if (this.start != null) {
			if (End.isBefore(this.start)) {
				throw new WrongInputException("Invalid date; Date is before start date");
			}
		}
		this.end = End;
	}

}
