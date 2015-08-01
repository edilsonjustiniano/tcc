package br.edu.univas.si.tcc.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.ServiceDAO;


@Path("/service")
public class ServiceResource {

	private ServiceDAO dao = new ServiceDAO();

	@Path("/getServices")
	@POST
	@Produces("application/json")
	public String getServices() {

		String responseService = dao.getAllService();

		JSONObject response = null;
		try {
			response = new JSONObject(responseService);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			response.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	
	@Path("/getServicesByName/{service}")
	@GET
	@Produces("application/json")
	public String getServicesByName(@PathParam("service") String service) {

		String responseService = dao.getServiceByName(service);

		JSONObject response = null;
		try {
			response = new JSONObject(responseService);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return response.toString();
	}

}
