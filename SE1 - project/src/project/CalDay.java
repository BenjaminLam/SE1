package project;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<CalDay> getDaysBetween(CalDay other){
		List<CalDay> daysBetween = new ArrayList<CalDay>();
		int thisWeek = this.week.week;
		int otherWeek = other.week.week;
		
		for(int i=thisWeek;i<=otherWeek; i++){
			for(int j=1; j<=5;j++){
				if(!(i==thisWeek && j<this.day)){
					if(!(i==otherWeek && j>other.day)){
						daysBetween.add(new CalDay(new CalWeek(this.week.year,i),j));
					}
				}
			}
		}
		return daysBetween; 
	}
}
