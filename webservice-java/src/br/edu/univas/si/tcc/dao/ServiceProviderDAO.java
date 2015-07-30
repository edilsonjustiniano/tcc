package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
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
						"RETURN DISTINCT(sp.name), sp.email, service.name LIMIT 10; \"}";
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


	public boolean isNewEvaluate(String providerEmail, String providerService, Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
//		MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}), 
//		(sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: 'mariaaparecida@gmail.com'}),
//		(service:Service {name: 'Doméstica'}),
//		(service)-[:EXECUTE]->(executed),
//		(sp)-[:EXECUTE]->(executed)-[:TO]->(me)
//		RETURN service.name, me.name, sp.name, COUNT(executed) as qtde;
		
		String query = "{\"query\":\" MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: '" + person.getEmail() + "'}), " +
					"(sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: '" + providerEmail + "'}), " + 
					"(service:Service {name: '" + providerService + "'}), " +
					"(service)-[:EXECUTE]->(executed), " +
					"(sp)-[:EXECUTE]->(executed)-[:TO]->(me) " + 
					"RETURN COUNT(executed) as qtde; \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
									.type(MediaType.APPLICATION_JSON).entity(query)
									.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
			
		JSONObject response = null;
		try {
			response = new JSONObject(objectCreate);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		int qtde = 0;
		try {
			String qtdeStr = response.getString("data");
			qtdeStr = qtdeStr.replaceAll("[^0-9]", "");
			qtde = Integer.parseInt(qtdeStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (qtde > 0) {
			return false;
		}
		return true;
	}


	public String saveEvaluate(String providerEmail, String providerService, int note, String comments, Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();

		String query = "{\"query\":\" MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: '" + person.getEmail() + "'}), " +
					"(sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: '" + providerEmail + "'}), " + 
					"(service:Service {name: '" + providerService + "'}) " +
					"CREATE (executed:Execute {note: '" + note + "', comments: '" + comments + "'}), " +
					"(service)-[:EXECUTE]->(executed), " +
					"(sp)-[:EXECUTE]->(executed)-[:TO]->(me) " + 
					"RETURN service.name, me.name, sp.name, COUNT(executed) as qtde; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
			
	}
	
	

}
