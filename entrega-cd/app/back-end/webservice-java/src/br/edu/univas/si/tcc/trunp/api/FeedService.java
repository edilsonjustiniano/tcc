package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface FeedService {

	public JSONObject lastestPartnership(String token) throws JSONException;
	public JSONObject lastestRatings(String token) throws JSONException;

}
