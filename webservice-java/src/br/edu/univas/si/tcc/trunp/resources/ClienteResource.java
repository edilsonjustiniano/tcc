package br.edu.univas.si.tcc.trunp.resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.controller.ClienteController;
import br.edu.univas.si.tcc.trunp.model.Cliente;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * 
 * @author edilson
 *
 */
@Path("/clientes")
public class ClienteResource {

	@GET
	@Path("/listarTodos")
	@Produces("application/json")
	public ArrayList<Cliente> listartTodos() {
		return new ClienteController().listartTodos();
	}
	
	
	@GET
	@Path("/listarUF")
	@Produces("application/json")
	public String listarUF() {
		
		String DATABASE_ENDPOINT = "http://localhost:7474/db/data";

        String DATABASE_USERNAME = "neo4j";

        String DATABASE_PASSWORD = "admin";

        String cypherUrl = DATABASE_ENDPOINT + "/cypher";
        
        String query = "MATCH (n:UF) RETURN n.id, n.name, n.abbreviation;";
        Client c = Client.create();
        c.addFilter(new HTTPBasicAuthFilter(DATABASE_USERNAME, DATABASE_PASSWORD));
        WebResource resource = c.resource(cypherUrl);
        
        /*
         * Try to create a node before 
         * 
         * CREATE A NODE VIA CYPHER
         */
//        String reqCreate = "{\"query\":\" CREATE (rj:UF {id: 3, name: 'Rio de Janeiro', abbreviation: 'RJ'}) RETURN rj; \"}";
//        ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
//                .type(MediaType.APPLICATION_JSON).entity(reqCreate)
//                .post(ClientResponse.class);
//        String objectCreate = responseCreate.getEntity(String.class);
        
        String request = "{\"query\":\"" + query + "\"}";
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).entity(request)
                .post(ClientResponse.class);
        
        //JS --
        /*
         * 	data.data[1][0].data
			Object {id: 2, name: "São Paulo", abbreviation: "SP"}
			
			data.data[0][0].data
			Object {id: 1, name: "Minas Gerais", abbreviation: "MG"}
         */
//        response.addHeader("Access-Control-Allow-Origin", "*");
        String object = response.getEntity(String.class);
        return object;
	}
	
	
	
	@Path("/addUF")
	@POST
	@Produces("application/json")
	public String addUF(String json) {
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
