package br.edu.univas.si.tcc.trunp.exception;

public abstract class TrunpException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrunpException() {
		super();
	}

	public TrunpException (String message) {
		super(message);
	}

	public TrunpException (Throwable cause) {
		super(cause);
	}

	public TrunpException (String message, Throwable cause) {
		super(message, cause);
	}

	public TrunpException (String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
