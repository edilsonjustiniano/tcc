package br.edu.univas.si.tcc.trunp.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.dao.CityDAO;

@Path("/city")
public class CityResource {

	private CityDAO dao = new CityDAO();
	
	@Path("/getAllCitiesByState/{state}")
	@GET
	@Produces("application/json")
	public String getAllCitiesByState(@PathParam("state") String state) {
		
		System.out.println(state);
		String responseCities = dao.getAllCitiesByState(state);
		
		JSONObject response = null;
		try {
			response = new JSONObject(responseCities);
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
