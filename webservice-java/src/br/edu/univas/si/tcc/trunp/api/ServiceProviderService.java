package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface ServiceProviderService {

	public JSONObject serviceProvidersByService(String data, String token) throws JSONException;
	public JSONObject ratingInMyNetworkPartners(String data);
	public JSONObject ratingInMyCompany(String data);
	public JSONObject ratingInMyCity(String data);
	public JSONObject data(String data);
	public JSONObject myServices(String token) throws JSONException;
	public JSONObject addService(String data);
	public JSONObject removeService(String data);
	
}