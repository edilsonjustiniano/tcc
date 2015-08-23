package br.edu.univas.si.tcc.trunp.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CityDAO {

	public String getAllCitiesByState(String state) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
        String query = "MATCH (uf:UF), (city:City), (city)-[:BELONGS_TO]->(uf)" +
        				" WHERE has(uf.name) AND UPPER(uf.name) = '" + state.toUpperCase() + "' RETURN DISTINCT(city.name);";
        
        String request = "{\"query\":\"" + query + "\"}";
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                							.type(MediaType.APPLICATION_JSON).entity(request)
                							.post(ClientResponse.class);
        
        String responseStr = response.getEntity(String.class);
		
		return responseStr;
	}
}
