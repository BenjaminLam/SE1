package project;

public class CalWeek {
	public int year;
	public int week;
	
	public CalWeek(int year, int week){
		this.year=year;
		this.week=week;
	}
	
	public boolean isAfterOrWhile (CalWeek week) {
		if (week.year<this.year) return true;
		if (week.year>this.year) return false;
		if (week.week<=this.week) return true;
		return false;
	}
	
	public boolean isBeforeOrWhile (CalWeek week) {
		if (week.year>this.year) return true;
		if (week.year<this.year) return false;
		if (week.week>=this.week) return true;
		return false;
	}
	
	public boolean equals(CalWeek other){
		if(this.year==other.year && this.week==other.week){
			return true;
		}
		return false;
	}
	
	public String toString() {
		String temp=("Year: " + year + " Week " + week);
		return temp;
	}
	
	public int weeksBetween(CalWeek other){
		int weeksBetween=0;
		if (other.year!=this.year) {
			weeksBetween+=Util.abs(other.year-this.year)*52;
			
			CalWeek max;
			CalWeek min;
			if (other.year>this.year) {
				max=other;
				min=this;
			}
			else {
				max=this;
				min=other;
			}
			
			weeksBetween+=max.week;
			weeksBetween+=52-min.week;
		}
		
		weeksBetween+=Util.abs(other.week-this.week);
	
		return weeksBetween;
	}
}
