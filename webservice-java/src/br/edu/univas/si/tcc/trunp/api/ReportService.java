package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONObject;

public interface ReportService {
	
	public JSONObject lastEvaluateOfServiceProvider(String token, String serviceProvider, String service, int limit);
	
	public JSONObject lastEvaluateOfServiceInNetwork(String token, String serviceProvider, String service, int limit);

}
