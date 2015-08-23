package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.SessionService;
import br.edu.univas.si.tcc.trunp.exception.JSONTrunpException;

@Path("/session")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionServiceImpl implements SessionService {
	
	@POST
	@Path("/login")
	public JSONObject login(String data) throws JSONTrunpException {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			throw new JSONTrunpException();
		}
		return json;
	}

	@GET
	@Path("/userinfo/{token}")
	public JSONObject userInfo(@PathParam("token") String token) throws JSONTrunpException {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			throw new JSONTrunpException();
		}
		return json;
	}

}
