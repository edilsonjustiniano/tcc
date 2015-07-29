package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.model.Person;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class ServiceProviderDAO {
	
	public String setServiceProvider(JSONObject jsonObj) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
        //String query = "{\"query\":\" CREATE (service:Service {name: '" + jsonObj.getString("name") +  "',";
        
        String request = "{\"query\":\"" + null + "\"}"; //só para parar de dar erro
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                							.type(MediaType.APPLICATION_JSON).entity(request)
                							.post(ClientResponse.class);
        
        String responseStr = response.getEntity(String.class);
		
		return responseStr;
	}

	
	public String getServiceProvidersByService(Person person, String service) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}), (service: Service), " + 
						"(sp)-[:PROVIDE]->(service) " +
						"WHERE service.name =~ '" + service + ".*' " + 
						"RETURN DISTINCT(sp.name), sp.email, service.name; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}


	public String getServiceProviderData(String providerEmail, String providerService) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: '" + providerEmail + "'}), " + 
							"(service:Service {name: '" + providerService + "'}), " +
							"(sp)-[:PROVIDE]->(service) " +
							"RETURN DISTINCT(sp.name), sp.email, sp.gender, service.name \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;

	}
	
	

}
