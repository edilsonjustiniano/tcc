package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface ServiceProviderService {

	public JSONObject serviceProvidersByService(String data, String token) throws JSONException;
	public JSONObject ratingInMyNetworkPartners(String provider, String service, String token) throws JSONException ;
	public JSONObject ratingInMyCompany(String provider, String service, String token) throws JSONException;
	public JSONObject ratingInMyCity(String provider, String service, String token) throws JSONException;
	public JSONObject data(String provider, String service, String token) throws JSONException;
	public JSONObject myServices(String token) throws JSONException;
	public JSONObject addService(String data) throws JSONException;
	public JSONObject removeService(String data) throws JSONException;
	
}
