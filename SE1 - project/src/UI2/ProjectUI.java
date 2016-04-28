package UI2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ProjectUI {
	Screen screen;
	
	public ProjectUI () {
		screen=new LoginScreen ();
	}
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader in=new BufferedReader (new InputStreamReader(System.in));
		PrintWriter out=new PrintWriter(System.out,true);
		ProjectUI ui=new ProjectUI();
		ui.basicLoop(in,out);
	}
	
	public void basicLoop(BufferedReader in, PrintWriter out) throws IOException {
		String selection;
		do {
			printMenu(out);
			selection=readInput(in);
		} while (!processInput(selection,out));
	}
	
	private void printMenu (PrintWriter out) {
		this.screen.printMenu(out);
	}
	
	private boolean processInput (String input, PrintWriter out) {
		return screen.processInput(input, out);
	}
	
	private String readInput (BufferedReader in) throws IOException {
		return in.readLine();
	}
	
	
	
	
	
}
