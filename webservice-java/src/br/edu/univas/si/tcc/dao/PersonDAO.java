package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
				" RETURN person.count as qtde; \"}";
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
		
		int qtde = 0;;
		try {
			qtde = response.getInt("qtde");
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
}
