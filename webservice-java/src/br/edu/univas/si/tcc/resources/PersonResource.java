package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@Path("/person")
public class PersonResource {

	@Path("/createAccountPersonalData")
	@POST
	@Produces("application/json")
	public String createAccountPersonalData(String json) {
		System.out.println(json);
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(json);
			
			System.out.println(jsonObj.toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		String DATABASE_ENDPOINT = "http://localhost:7474/db/data";

        String DATABASE_USERNAME = "neo4j";

        String DATABASE_PASSWORD = "admin";

        String cypherUrl = DATABASE_ENDPOINT + "/cypher";
        
        Client c = Client.create();
        c.addFilter(new HTTPBasicAuthFilter(DATABASE_USERNAME, DATABASE_PASSWORD));
        WebResource resource = c.resource(cypherUrl);
		
		String reqCreate = null;
		try {
			reqCreate = "{\"query\":\" CREATE (uf:UF {id: " + jsonObj.getInt("id") + ", name: '" + jsonObj.getString("name") + "', abbreviation: '" + jsonObj.getString("abbreviation") + "'}) RETURN uf.id, uf.name, uf.abbreviation; \"}";
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
              .type(MediaType.APPLICATION_JSON).entity(reqCreate)
              .post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		JSONObject response = null;
		try {
			response = new JSONObject(objectCreate);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			response.put("success", true);
			response.put("mesage", "Sucesso ao inserir UF!");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}
}
