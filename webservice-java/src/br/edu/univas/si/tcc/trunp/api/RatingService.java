package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface RatingService {
	
	public JSONObject save(String data) throws JSONException;
	public JSONObject myLastestRatings(String token) throws JSONException;

}
