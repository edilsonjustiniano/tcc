package br.edu.univas.si.tcc.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/**
	 * generateMD5
	 * @param password
	 * @return password encrypted
	 */
	public static String generateMD5(String password){
		
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0,password.length());
			//System.out.println(new BigInteger(1,m.digest()).toString(16));
			return (new BigInteger(1,m.digest()).toString(16));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
