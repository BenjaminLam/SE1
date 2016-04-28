package project;

public class CalDay {
	public CalWeek week;
	public int day;
	
	public CalDay (CalWeek week, int day) {
		this.week=week;
		this.day=day;
	}
	
	public boolean equals(CalDay other){
		if((this.day==other.day)&&(this.week.equals(other.week))){
			return true;
		}
		return false;
	}
}
