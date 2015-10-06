package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface ReportService {
	
	public JSONObject lastEvaluateOfServiceProvider(String token, String serviceProvider, String service, int limit) throws JSONException;
	
	public JSONObject lastEvaluateOfServiceInNetwork(String token, String serviceProvider, String service, int limit) throws JSONException;

	public JSONObject lastEvaluate(String serviceProvider, String service, int limit) throws JSONException;
	
	public JSONObject lastEvaluateInMyCity(String serviceProvider, String service, int limit) throws JSONException;
	
}
