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

import br.edu.univas.si.tcc.trunp.api.PersonService;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonServiceImpl implements PersonService {

	@POST
	@Path("/createaccount/personaldata")
	public JSONObject accountPersonalData(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@POST
	@Path("/createaccount/workdata")
	public JSONObject accountWorkData(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/persondata/{token}")
	public JSONObject personData(@PathParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	
}
