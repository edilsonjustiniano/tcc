package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.model.Person;
import br.edu.univas.si.tcc.util.MD5Util;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PersonDAO {

	/**
	 * Method to check if the email informed by user is a free email or not
	 * @param email
	 * @return
	 */
	public boolean isFreeEmail(String email) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (person:Person {email: '" + email + "'})" +
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
			return false;
		}
		return true;
	}
	
	
	/**
	 * Method that will be called when user register a new account
	 * @param jsonObj
	 * @return
	 */
	public String createAccountPersonalData(JSONObject jsonObj) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		try {
			String password = MD5Util.generateMD5(jsonObj.getString("password"));
			query = "{\"query\":\" CREATE (person:Person {name: '" + jsonObj.getString("name") + "'," +
								" email: '" + jsonObj.getString("email") + "'," +
								" password: '" + password + "'," +
								" typeOfAccount: '" + jsonObj.getString("typeOfAccount") + "'," +
								" typeOfPerson: '" + jsonObj.getString("typeOfPerson") + "',";
			
			if (jsonObj.getString("typeOfPerson").equals("PHISIC")) {
				query += " cpf: '" + jsonObj.getString("cpf") + "', " +
							 " gender: '" + jsonObj.getString("gender") + "'";
			} else {
				query += " cnpj: " + jsonObj.getString("cnpj") + "'";
			}
			
			query += "}) RETURN person.name, person.email, person.password; \"}";
			System.out.println(query);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
	              					.type(MediaType.APPLICATION_JSON).entity(query)
	              					.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}


	public String createAccountWorkData(Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (cityWorks:City {name: '" + person.getWorksIn().getLocatedIn().getName() + "'}), " +
							"(cityLives:City {name: '" + person.getLivesIn().getName() + "'}), " +
							"(ufWorks:UF {name: '" + person.getWorksIn().getLocatedIn().getUf().getName() + "'}), " +
							"(ufLives:UF {name: '" + person.getWorksIn().getLocatedIn().getUf().getName() + "'}), " +
							"(company:Company {name: '" + person.getWorksIn().getName() + "'}), " +
							"(person:Person {email: '" + person.getEmail() + "'}), " +
							"(company)-[:LOCATED_IN]->(cityWorks), " +
							"(cityWorks)-[:BELONGS_TO]->(ufWorks), " +
							"(cityLives)-[:BELONGS_TO]->(ufLives) " + 
							"CREATE (person)-[:LIVES_IN]->(cityLives), " +
							"(person)-[:WORKS_IN]->(company) " + 
							"SET person += {district: '" + person.getDistrict() + "', " +
							"address: '" + person.getAddress() + "'} " +
							"RETURN person.name, person.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}
}
