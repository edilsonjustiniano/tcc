package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.ServiceProviderDAO;


@Path("/serviceProvider")
public class ServiceProviderResource {
	
	private ServiceProviderDAO dao = new ServiceProviderDAO();
	
@Path("/setServices")
@POST
@Produces("application/json")
	public String setServices() {

		String responseService = dao.setServiceProvider(null);

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


	

}
