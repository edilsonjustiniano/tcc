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

import br.edu.univas.si.tcc.trunp.api.ServiceService;
import br.edu.univas.si.tcc.trunp.controller.ServiceController;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

@Path("/services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceServiceImpl implements ServiceService {

	private ServiceController serviceController = new ServiceController();
	
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
	@Path("/service/{name}")
	public JSONObject servicesByName(@PathParam("name") String service) throws JSONException {
		
		JSONArray result = serviceController.getServiceByName(service);
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, result);

		return json;
		
	} 
}
