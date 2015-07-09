package br.edu.univas.si.tcc.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.StateDAO;

@Path("/state")
public class StateResource {
	
	private StateDAO dao = new StateDAO();
	
	@Path("/getAllStates")
	@GET
	@Produces("application/json")
	public String getAllStates() {
		
		String responseStates = dao.getAllStates();
		
		JSONObject response = null;
		try {
			response = new JSONObject(responseStates);
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
