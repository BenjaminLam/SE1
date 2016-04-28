package UI;

import java.util.List;
import java.util.Scanner;

public class UI {
	Scanner scanner=new Scanner (System.in);
	
	public UI () {
		
	}
	
	public int getInt (String ) {
		
	}
	
	public void printList (List<Object> list) {
		for (Object object:list) {
			System.out.println(object);
		}
	}
	
	public int getIntBetween (int min, int max) {
		boolean numberChosen=false;
		int number;
		System.out.println("Please choose a number between " + min + " and " + max + ":");
		
		while (!numberChosen) {
			number=scanner.nextInt();
			
			if (correctNumber(number,min,max)) numberChosen=true;
			if (!numberChosen) System.out.println("Wrong number. Please try again");	
		}
		return number;
	}
	
	private boolean correctNumber (int number, int min, int max) {
		if (number<min) return false;
		if (number>max) return false;
		return true;
	}
	
	
	public void showText
	
	
	public void logIn () {
		boolean loggedIn=false;
		
		System.out.println("Please enter employeeID");
		
		while (!loggedIn) {
			int userInput=scanner.nextInt();
			if (logIn(userInput)) loggedIn=true;
			
			if (!loggedIn) System.out.println("You've entered wrong ID. Please try agagin");
		}
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
