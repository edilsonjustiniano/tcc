package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONObject;

public interface ServiceService {
	
	public JSONObject services();
	public JSONObject servicesByName(String service);

}
