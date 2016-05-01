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
	
	public Task (Project project, String name) {
		this.name=name;
		this.projectID=project.ID;
	}
	
	public boolean inPast() throws WrongInputException {
		if (end==null) throw new WrongInputException("Week doesn't excist");
		return end.isBefore(getCurrentWeek());
	}
	
	private CalWeek getCurrentWeek () {
		Calendar calendar=new GregorianCalendar();
		int year=calendar.get(Calendar.YEAR);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		
		return new CalWeek(year,week);
	}
	
	public void setTaskStart (CalWeek start, Employee employee) throws WrongInputException{
		CalWeek currentWeek = getCurrentWeek();
		if (start.isBefore(currentWeek)){
			throw new WrongInputException ("Invalid date; Date is in the past");
		}
		if (this.end != null){
			if (start.isAfter(this.end)){
			throw new WrongInputException("Invalid date; Date is after task end date");
			}
		}
		this.start = start;
	}
	public void setTaskEnd (CalWeek End, Employee employee) throws WrongInputException{
		CalWeek currentWeek = getCurrentWeek();
		if (End.isBefore(currentWeek)){
			throw new WrongInputException ("Invalid date; Date is in the past");
		}
		this.end = End;
	}

	
}
