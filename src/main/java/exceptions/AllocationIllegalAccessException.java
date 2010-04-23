package exceptions;

public class AllocationIllegalAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8030364198265688548L;

	public AllocationIllegalAccessException() {
	}

	public AllocationIllegalAccessException(String message) {
		super(message);
	}

	public AllocationIllegalAccessException(Throwable cause) {
		super(cause);
	}

	public AllocationIllegalAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
