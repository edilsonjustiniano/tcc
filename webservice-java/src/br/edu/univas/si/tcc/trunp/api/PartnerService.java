package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface PartnerService {

	public JSONObject possiblePartners(String token) throws JSONException;
	public JSONObject allPartners(String token)  throws JSONException;
	public JSONObject add(String data);
	public JSONObject cancel(String data);
	public JSONObject allPartnerRequest(String token) throws JSONException;
	public JSONObject acceptPartnerRequest(String data) throws JSONException;
	public JSONObject rejectPartnerRequest(String data) throws JSONException;
	public JSONObject searchNewPartners(String data);
	public JSONObject searchNewPartnersOnlyByName(String data);
	public JSONObject isMyPartner(String provider, String token) throws JSONException;
	public JSONObject commonsPartners(String partner, String token)  throws JSONException;
}
