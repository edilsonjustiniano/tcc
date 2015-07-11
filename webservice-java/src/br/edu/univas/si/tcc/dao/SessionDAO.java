package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.model.Person;

public class SessionDAO {
	
	public boolean thereIsPersonOnDatabase(Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (person:Person {email: '" + person.getEmail() + "', " + 
				"password: '" + person.getPassword() + "'})" +
				" RETURN COUNT(person) as qtde; \"}";
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
			return true;
		}
		return false;
	}

	
	public String getPersonDataInSession(Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (person:Person {email: '" + person.getEmail() + "', " + 
				"password: '" + person.getPassword() + "'})" +
				" RETURN person.name, person.email; \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
									.type(MediaType.APPLICATION_JSON).entity(query)
									.post(ClientResponse.class);
		String result = responseCreate.getEntity(String.class);
			
		return result;
	}
}
