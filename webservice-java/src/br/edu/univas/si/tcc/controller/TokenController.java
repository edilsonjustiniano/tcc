package br.edu.univas.si.tcc.controller;

import java.util.Date;

import br.edu.univas.si.tcc.model.Token;

public class TokenController {

	private static final int MINUTES_OF_SESSION_ACTIVE = 15; //15min
	
	/* Method to check if session is still alive */
	public boolean isExpiredSession(Token token) {
		
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		
		Date currentTime = new Date();
		Date timeLastAccess = new Date(token.getLastAccessTime() + (MINUTES_OF_SESSION_ACTIVE * ONE_MINUTE_IN_MILLIS));
		
		if (timeLastAccess.before(currentTime)) {
			return true; //session already expired
		}
		return false; //session activate yet
	}
}