package UI;

import java.util.List;
import java.util.Scanner;

public class UI {
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
