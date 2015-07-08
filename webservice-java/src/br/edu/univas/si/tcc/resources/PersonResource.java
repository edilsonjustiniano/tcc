package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.PersonDAO;

@Path("/person")
public class PersonResource {
	
	private PersonDAO dao = new PersonDAO();

	@Path("/createAccountPersonalData")
	@POST
	@Produces("application/json")
	public String createAccountPersonalData(String json) {
		System.out.println(json);
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(json);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		/* Check if the email is free or not */
		try {
			if (!dao.isFreeEmail(jsonObj.getString("email"))) {
				JSONObject response = null;
				response = new JSONObject();
				response.put("success", false);
				response.put("mesage", "Email já registrado, por favor utilize outro!");
				return response.toString();
			}
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		
//		String DATABASE_ENDPOINT = "http://localhost:7474/db/data";
//
//        String DATABASE_USERNAME = "neo4j";
//
//        String DATABASE_PASSWORD = "admin";
//
//        String cypherUrl = DATABASE_ENDPOINT + "/cypher";
//        
//        Client c = Client.create();
//        c.addFilter(new HTTPBasicAuthFilter(DATABASE_USERNAME, DATABASE_PASSWORD));
//        WebResource resource = c.resource(cypherUrl);
		
		String objectCreate = dao.createAccountPersonalData(jsonObj);
		
		JSONObject response = null;
		try {
			response = new JSONObject(objectCreate);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			response.put("success", true);
			response.put("mesage", "Sucesso ao cadastrar a nova conta!");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response.toString();
	}
}
