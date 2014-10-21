package exceptions;

public class InsufficientSharesException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientSharesException() {
	}

	public InsufficientSharesException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientSharesException(String message) {
		super(message);
	}

	public InsufficientSharesException(Throwable cause) {
		super(cause);
	}

}
