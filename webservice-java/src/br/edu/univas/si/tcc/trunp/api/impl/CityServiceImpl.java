package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.CityService;
import br.edu.univas.si.tcc.trunp.controller.CityController;
import br.edu.univas.si.tcc.trunp.exception.JSONTrunpException;
import br.edu.univas.si.tcc.trunp.exception.TrunpException;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

@Path("/city")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CityServiceImpl implements CityService {

	private CityController cityController = new CityController();
	
	@GET
	@Path("/cities/{state}")
	public JSONObject cities(@PathParam("state") String state) throws JSONException {
		
		System.out.println(state);
		JSONArray responseCities = cityController.getAllCitiesByState(state);
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, responseCities);
		
		return json;
	}
	
}
