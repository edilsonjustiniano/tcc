package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.CityService;
import br.edu.univas.si.tcc.trunp.exception.JSONTrunpException;
import br.edu.univas.si.tcc.trunp.exception.TrunpException;

@Path("/city")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CityServiceImpl implements CityService {

	@GET
	@Path("/cities/{state}")
	public JSONObject cities(@PathParam("state") String state) throws JSONTrunpException {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			throw new JSONTrunpException("Falha ao manipular as respostas", e);
		}
		return json;
	}
	
	@GET
	@Path("/citiesByQuery")
	public JSONObject citiesByQuery(@QueryParam("state") String state) throws JSONTrunpException {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			throw new JSONTrunpException("Falha ao manipular as respostas", e);
		}
		return json;
	}

}
