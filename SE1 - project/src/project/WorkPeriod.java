package project;

public class WorkPeriod {
	public CalDay day;
	private double start;
	private double end;
	
	public WorkPeriod (CalDay day, int start, int end) {
		this.day=day;
		this.start=start;
		this.end=end;
	}
	
	public double getLength () {
		return end-start;
	}
	
	public boolean isAfter (CalWeek week) {
		if (week.year<this.day.year) return true;
		if (week.year>this.day.year) return false;
		if (week.week<=this.day.week) return true;
		return false;
	}
	
	public boolean isBefore (CalWeek week) {
		if (week.year>this.day.year) return true;
		if (week.year<this.day.year) return false;
		if (week.week>=this.day.week) return true;
		return false;
	}
}
