package br.edu.univas.si.tcc.trunp.dao;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PersonDAO {

	private static final String UPLOAD_DIR = "upload/";

	/**
	 * Method to check if the email informed by user is a free email or not
	 * 
	 * @param email
	 * @return
	 */
	public boolean isFreeEmail(String email) {

		WebResource resource = FactoryDAO.GetInstance();

		String query = null;
		query = "{\"query\":\" MATCH (person:Person {email: '" + email + "'})"
				+ " RETURN COUNT(person) as qtde; \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */

		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
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
	 * 
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 */
	public JSONArray createAccountPersonalData(JSONObject jsonObj)
			throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();

		String query = null;
		String password = MD5Util.generateMD5(jsonObj.getString("password"));
		query = "{\"query\":\" CREATE (person:Person {name: '"
				+ jsonObj.getString("name") + "'," + " email: '"
				+ jsonObj.getString("email") + "'," + " password: '" + password
				+ "'," + " typeOfAccount: '"
				+ jsonObj.getString("typeOfAccount") + "',"
				+ " typeOfPerson: '" + jsonObj.getString("typeOfPerson") + "',";

		if (jsonObj.getString("typeOfPerson").equals("PHISIC")) {
			query += " cpf: '" + jsonObj.getString("cpf") + "', "
					+ " gender: '" + jsonObj.getString("gender") + "'";
		} else {
			query += " cnpj: " + jsonObj.getString("cnpj") + "'";
		}

		query += "}) RETURN {name: person.name, email: person.email, password: person.password} as person; \"}";
		System.out.println(query);

		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
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

	public JSONArray createAccountWorkData(Person person) throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();

		String query = null;
		query = "{\"query\":\" MATCH (cityWorks:City {name: '"
				+ person.getWorksIn().getLocatedIn().getName() + "'}), "
				+ "(cityLives:City {name: '" + person.getLivesIn().getName()
				+ "'}), " + "(ufWorks:UF {name: '"
				+ person.getWorksIn().getLocatedIn().getUf().getName()
				+ "'}), " + "(ufLives:UF {name: '"
				+ person.getWorksIn().getLocatedIn().getUf().getName()
				+ "'}), " + "(company:Company {name: '"
				+ person.getWorksIn().getName() + "'}), "
				+ "(person:Person {email: '" + person.getEmail() + "'}), "
				+ "(company)-[:LOCATED_IN]->(cityWorks), "
				+ "(cityWorks)-[:BELONGS_TO]->(ufWorks), "
				+ "(cityLives)-[:BELONGS_TO]->(ufLives) "
				+ "WITH DISTINCT(person), cityLives, company "
				+ "CREATE (person)-[:LIVES_IN]->(cityLives), "
				+ "(person)-[:WORKS_IN]->(company) "
				+ "SET person += {district: '" + person.getDistrict() + "', "
				+ "address: '" + person.getAddress() + "'} "
				+ "RETURN DISTINCT({name: person.name, email: person.email}) as person; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
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

	/**
	 * Used to get all data of person to show the profile data
	 * 
	 * @param partnerEmail
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray getPersonData(String partnerEmail) throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();

		String query = null;
		query = "{\"query\":\" MATCH (partner:Person {email: '"
				+ partnerEmail
				+ "'}), (city:City), "
				+ "(company:Company), "
				+ "(partner)-[:LIVES_IN]->(city), "
				+ "(partner)-[:WORKS_IN]->(company) "
				+ "RETURN DISTINCT({name: partner.name, email: partner.email, photo: partner.photo, city: city.name, " +
				"company: company.name, cpf: partner.cpf, cnpj: partner.cnpj, typeOfPerson: partner.typeOfPerson, " +
				"gender: partner.gender}) as partner; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
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

	public String setPhoto(String email, String photoName) {

		WebResource resource = FactoryDAO.GetInstance();

		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + email + "'}) "
				+ "SET me += {photo: '" + UPLOAD_DIR + photoName + "'} "
				+ "RETURN me.name, me.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(query)
				.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);

		return objectCreate;

	}

	public JSONArray edit(Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();

		String query = "{\"query\":\" MATCH (cityWorks:City {name: '"
				+ person.getWorksIn().getLocatedIn().getName() + "'}), "
				+ "(cityLives:City {name: '" + person.getLivesIn().getName()
				+ "'}), (oldCityLives:City), (ufWorks:UF {name: '"
				+ person.getWorksIn().getLocatedIn().getUf().getName()
				+ "'}), " + "(ufLives:UF {name: '"
				+ person.getLivesIn().getUf().getName()
				+ "'}), (oldUfLives:UF), (newCompany:Company {name: '"
				+ person.getWorksIn().getName() + "'}), (oldCompany:Company), "
				+ "(person:Person {email: '" + person.getEmail() + "'}), "
				+ "(person)-[oldWorksIn:WORKS_IN]->(oldCompany), "
				+ "(person)-[oldLivesInCity:LIVES_IN]->(oldCityLives), "
				+ "(newCompany)-[:LOCATED_IN]->(cityWorks), "
				+ "(cityWorks)-[:BELONGS_TO]->(ufWorks) "
				+ "WITH DISTINCT(person), cityLives, newCompany, oldWorksIn, oldLivesInCity "
				+ "DELETE oldWorksIn, oldLivesInCity "
				+ "CREATE (person)-[:LIVES_IN]->(cityLives), "
				+ "(person)-[:WORKS_IN]->(newCompany) "
				+ "SET person += {district: '" + person.getDistrict() + "', "
				+ "address: '" + person.getAddress() + "', name: '" + person.getName() + "', "
				+ "email: '" + person.getEmail() + "', typeOfAccount: '" + person.getTypeOfAccount() + "', " 
				+ "typeOfPerson: '" + person.getTypeOfPerson() + "'} "
				+ "RETURN DISTINCT({name: person.name, email: person.email}) as person; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
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

	public JSONArray changePassword(String email, String newPassword) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();

		String query = "{\"query\":\" MATCH (person:Person {email: '"+ email + "'}) "
				+ "SET person += {password: '" + newPassword + "'} "
				+ "RETURN DISTINCT({name: person.name, email: person.email}) as person; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource
				.accept(MediaType.APPLICATION_JSON)
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
