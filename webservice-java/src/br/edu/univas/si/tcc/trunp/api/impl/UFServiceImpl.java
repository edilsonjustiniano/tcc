package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.UFService;
import br.edu.univas.si.tcc.trunp.exception.JSONTrunpException;

@Path("/uf")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UFServiceImpl implements UFService {
	
	@GET
	public JSONObject uf() throws JSONTrunpException {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			throw new JSONTrunpException();
		}
		return json;
	}

}
