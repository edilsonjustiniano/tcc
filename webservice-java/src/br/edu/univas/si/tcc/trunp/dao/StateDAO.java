package br.edu.univas.si.tcc.trunp.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class StateDAO {

	public String getAllStates() {
		
		WebResource resource = FactoryDAO.GetInstance();
		
        String query = "MATCH (uf:UF) RETURN uf.name, uf.abbreviation;";
        
        String request = "{\"query\":\"" + query + "\"}";
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                							.type(MediaType.APPLICATION_JSON).entity(request)
                							.post(ClientResponse.class);
        
        String responseStr = response.getEntity(String.class);
		
		return responseStr;
	}
}
