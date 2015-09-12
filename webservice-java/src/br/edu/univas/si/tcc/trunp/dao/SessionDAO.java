package br.edu.univas.si.tcc.trunp.dao;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

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

	
	public JSONArray getPersonDataInSession(Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (person:Person {email: '" + person.getEmail() + "'}), " +
				"(cityLives:City), (ufLives:UF), (cityWork:City), (ufWork:UF), (company:Company),"+
				"(person)-[:LIVES_IN]->(cityLives), "+
				"(cityLives)-[:BELONGS_TO]->(ufLives), "+
				"(person)-[:WORKS_IN]->(company), "+
				"(company)-[:LOCATED_IN]->(cityWork) "+
				" RETURN DISTINCT({name: person.name, email: person.email, typeOfPerson: person.typeOfPerson, "  + 
				"typeOfAccount: person.typeOfAccount, photo: person.photo, address: person.address, " +
				"cpf: person.cpf, cnpj: person.cnpj, district: person.district, cityLives: cityLives.name, " +
				"ufLives: ufLives.name, cityWork: cityWork.name, ufWork: ufWork.name, company: company.name}) as person; \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
									.type(MediaType.APPLICATION_JSON).entity(query)
									.post(ClientResponse.class);
		String resp = responseCreate.getEntity(String.class);
		
		JSONObject json = null;
		JSONArray objData = null;
		json = new JSONObject(resp);
		objData = json.getJSONArray("data");
		List<JSONObject> parser = JSONUtil.parseJSONArrayToListJSON(objData);
		System.out.println(parser);

		JSONArray arr = new JSONArray(parser);
		System.out.println(arr);

		return arr;
	}
}
