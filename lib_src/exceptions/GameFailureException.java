package exceptions;

public class GameFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	public GameFailureException(String message, Exception e) {
		super(message, e);
	}

}
