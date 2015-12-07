package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.UFService;
import br.edu.univas.si.tcc.trunp.controller.UFController;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

@Path("/uf")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UFServiceImpl implements UFService {
	
	private UFController ufController = new UFController();
	
	@GET
	public JSONObject uf() throws JSONException {
		
		JSONArray responseStates = ufController.getUfs();  //dao.getAllStates();
		
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, responseStates);
		return json;
	}
}
