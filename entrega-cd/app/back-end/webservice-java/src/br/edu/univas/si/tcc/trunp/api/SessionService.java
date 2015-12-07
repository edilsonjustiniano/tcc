package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.exception.JSONTrunpException;

public interface SessionService {

	public JSONObject login(String json) throws JSONException;
	public JSONObject userInfo(String token) throws JSONException;
}
