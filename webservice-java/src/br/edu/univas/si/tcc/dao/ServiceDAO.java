package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ServiceDAO {
	
public String getAllService() {
		
		WebResource resource = FactoryDAO.GetInstance();
		
        String query = "MATCH (service:Service) RETURN service.name;";
        
        String request = "{\"query\":\"" + query + "\"}";
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                							.type(MediaType.APPLICATION_JSON).entity(request)
                							.post(ClientResponse.class);
        
        String responseStr = response.getEntity(String.class);
		
		return responseStr;
	}

}