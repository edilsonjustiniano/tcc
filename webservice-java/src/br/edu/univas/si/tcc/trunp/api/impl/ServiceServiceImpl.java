package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.ServiceService;

@Path("/services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceServiceImpl implements ServiceService {

	@GET
	public JSONObject services() {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@GET
	@Path("/service")
	public JSONObject servicesByName(@QueryParam("name") String service) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
			json.put("service", service);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
		
	} 
}
