package project;

public class CalWeek {
	public int year;
	public int week;
	
	public CalWeek(int year, int week){
		this.year=year;
		this.week=week;
	}
	
	public boolean isAfter (CalWeek week) {
		if (week.year<this.year) return true;
		if (week.year>this.year) return false;
		if (week.week<=this.week) return true;
		return false;
	}
	
	public boolean isBefore (CalWeek week) {
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
}
