package br.edu.univas.si.tcc.trunp.bi;

import java.util.Date;

import br.edu.univas.si.tcc.trunp.dao.TokenDAO;
import br.edu.univas.si.tcc.trunp.model.Token;

public class TokenBi {
	
	private static final int MINUTES_OF_SESSION_ACTIVE = 15; //15min
	
	private TokenDAO dao;
	
	public TokenBi() {
		this.dao = new TokenDAO();
	}
	
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
	
	public boolean isValidToken(String token) {
		if (token == null || token.equals("")) {
			return false;
		} 
		return true;
		
	}
	
	public boolean isValidSession(Token token) {
		return dao.isValidSession(token);
	}

}
