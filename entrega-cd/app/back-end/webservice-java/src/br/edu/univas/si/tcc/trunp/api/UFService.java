package br.edu.univas.si.tcc.trunp.api;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.exception.TrunpException;

public interface UFService {

	public JSONObject uf() throws JSONException;
}
