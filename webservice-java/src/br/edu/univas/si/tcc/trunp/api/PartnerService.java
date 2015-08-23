package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONObject;

public interface PartnerService {

	public JSONObject possiblePartners(String data);
	public JSONObject allPartners(String data);
	public JSONObject add(String data);
	public JSONObject cancel(String data);
	public JSONObject allPartnerRequest(String data);
	public JSONObject acceptPartnerRequest(String data);
	public JSONObject rejectPartnerRequest(String data);
	public JSONObject searchNewPartners(String data);
	public JSONObject searchNewPartnersOnlyByName(String data);
	public JSONObject isMyPartner(String data);
	public JSONObject commonsPartners(String data);
}
