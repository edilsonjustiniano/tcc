package br.edu.univas.si.tcc.util;

import br.edu.univas.si.tcc.model.Token;

import com.sun.jersey.core.util.Base64;

public class Base64Util {

	public static final String BASE64_TOKEN_SEPARATOR = "|";
	
	public static byte[] encodeToken(String email, String password) {
		byte[] token = Base64.encode(email + BASE64_TOKEN_SEPARATOR + password);
		return token;
	}
	
	public static Token decodeToken(byte[] tokenDecoded) {
		Token token = new Token();
		
		byte[] bytes = Base64.decode(tokenDecoded);
		String tokenStr = new String(bytes);
		
		String[] splitStr = tokenStr.split("\\" + BASE64_TOKEN_SEPARATOR);
		token.setEmail(splitStr[0]);
		token.setPassword(splitStr[1]);
		
		return token;
	}
}
