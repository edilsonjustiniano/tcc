package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONObject;

public interface FeedService {

	public JSONObject lastestPartnership(String json);
	public JSONObject lastestRatings(String json);

}
