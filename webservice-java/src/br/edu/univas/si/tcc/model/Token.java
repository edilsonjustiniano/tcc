package br.edu.univas.si.tcc.model;

import br.edu.univas.si.tcc.util.Base64Util;

public class Token {

	private String email;
	private String password;
	private Long lastAccessTime;
	
	/**
	 * GET/SET
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(Long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	@Override
	public String toString() {
		return this.email + Base64Util.BASE64_TOKEN_SEPARATOR + this.password + Base64Util.BASE64_TOKEN_SEPARATOR + this.lastAccessTime;
	}
}
