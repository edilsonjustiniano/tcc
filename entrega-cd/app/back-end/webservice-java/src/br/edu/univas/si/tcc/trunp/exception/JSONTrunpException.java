package br.edu.univas.si.tcc.trunp.exception;

public class JSONTrunpException extends TrunpException {
	
	public JSONTrunpException() {
		super();
	}

	public JSONTrunpException (String message) {
		super(message);
	}

	public JSONTrunpException (Throwable cause) {
		super(cause);
	}

	public JSONTrunpException (String message, Throwable cause) {
		super(message, cause);
	}

	public JSONTrunpException (String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
