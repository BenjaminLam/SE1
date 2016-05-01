package Exceptions_Errors;

public class WrongInputException extends Exception {
	
	
	public WrongInputException (String errorMessage) {
		super(errorMessage);
	}
}
