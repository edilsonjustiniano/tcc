package br.edu.univas.si.tcc.trunp.controller;

import br.edu.univas.si.tcc.trunp.bi.TokenBi;
import br.edu.univas.si.tcc.trunp.model.Token;

public class TokenController {

	private static final int MINUTES_OF_SESSION_ACTIVE = 15; //15min
	
	private TokenBi tokenBi;
	
	public TokenController() {
		this.tokenBi = new TokenBi();
	}
	
	/* Method to check if session is still alive */
	public boolean isExpiredSession(Token token) {
		return tokenBi.isExpiredSession(token);
	}
	
	
	public boolean isValidToken(String token) {
		return tokenBi.isValidToken(token);
	}
	
	public boolean isValidSession(Token token) {
		return tokenBi.isValidSession(token);
	}
	
}
