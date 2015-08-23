package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONObject;

public interface PersonService {

	public JSONObject accountPersonalData(String data);
	public JSONObject accountWorkData(String data);
	public JSONObject personData(String token);
	
}
