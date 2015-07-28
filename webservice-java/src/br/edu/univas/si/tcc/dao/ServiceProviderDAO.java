package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class ServiceProviderDAO {
public String setServiceProvider(JSONObject jsonObj) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
        //String query = "{\"query\":\" CREATE (service:Service {name: '" + jsonObj.getString("name")"',";
        
        String request = "{\"query\":\"" + query + "\"}";
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                							.type(MediaType.APPLICATION_JSON).entity(request)
                							.post(ClientResponse.class);
        
        String responseStr = response.getEntity(String.class);
		
		return responseStr;
	}

}
