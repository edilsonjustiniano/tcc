package br.edu.univas.si.tcc.util;

import java.sql.Timestamp;
import java.util.Date;

import br.edu.univas.si.tcc.model.Token;

import com.sun.jersey.core.util.Base64;

public class Base64Util {

	public static final String BASE64_TOKEN_SEPARATOR = "|";
	
	public static byte[] encodeToken(String email, String password) {
		/* Get Current Time in order to check if the session is valid yet */
		Long currentTime = new Timestamp(new Date().getTime()).getTime();
		System.out.println(currentTime);
		byte[] token = Base64.encode(email + BASE64_TOKEN_SEPARATOR + password + BASE64_TOKEN_SEPARATOR + currentTime);
		return token;
	}
	
	public static Token decodeToken(byte[] tokenDecoded) {
		Token token = new Token();
		
		byte[] bytes = Base64.decode(tokenDecoded);
		String tokenStr = new String(bytes);
		
		String[] splitStr = tokenStr.split("\\" + BASE64_TOKEN_SEPARATOR);
		token.setEmail(splitStr[0]);
		token.setPassword(splitStr[1]);
		token.setLastAccessTime(Long.parseLong(splitStr[2]));
		
		return token;
	}
}
