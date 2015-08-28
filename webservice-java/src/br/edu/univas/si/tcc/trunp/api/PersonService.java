package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface PersonService {

	public JSONObject accountPersonalData(String data) throws JSONException;
	public JSONObject accountWorkData(String data) throws JSONException;
	public JSONObject personData(String partner, String token) throws JSONException;
	
}
