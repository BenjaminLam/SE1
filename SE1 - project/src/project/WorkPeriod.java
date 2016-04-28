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
	
	public boolean equals(WorkPeriod other){
		if(this.day.equals(other.day) && this.start==other.start && this.end==other.end){
			return true;
		}
		return false;
	}
}
