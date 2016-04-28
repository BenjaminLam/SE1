package UI2;

import java.io.PrintWriter;

public abstract class Screen {
	public abstract boolean processInput(String input, PrintWriter out);
	public abstract void printMenu(PrintWriter out);
}
