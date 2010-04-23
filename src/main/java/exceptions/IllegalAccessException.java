package exceptions;

public class IllegalAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8030364198265688548L;

	public IllegalAccessException() {
	}

	public IllegalAccessException(String message) {
		super(message);
	}

	public IllegalAccessException(Throwable cause) {
		super(cause);
	}

	public IllegalAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
