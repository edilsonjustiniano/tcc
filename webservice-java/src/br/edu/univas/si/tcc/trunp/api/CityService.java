package br.edu.univas.si.tcc.trunp.api;

import javax.ws.rs.PathParam;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface CityService {
	
	public JSONObject cities(@PathParam("state") String state) throws JSONException;

}
