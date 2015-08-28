package br.edu.univas.si.tcc.trunp.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

public class PartnerDAO {

	public JSONArray getPossiblePartners(Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		
		/*query = "{\"query\":\" MATCH (cityWorks:City {name: '" + person.getWorksIn().getLocatedIn().getName() + "'}), " +
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
		*/
//		OLD QUERY, BUT WORKING TOO
//		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), (users:Person), " +
//				"(users)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me) " +
//				"WHERE users <> me AND (NOT( (me-[:PARTNER_OF]->users) OR (users-[:PARTNER_OF]->me))) " + 
//				"RETURN DISTINCT(users.name), users.email, company.name, 1 as length; \"}";
		
		//NEW
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(users:Person), " +
							"(users)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me) " +
							"WHERE users <> me AND users.typeOfAccount <> 'SERVICE_PROVIDER' " +
							"AND NOT((users)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(users)) " + 
							"OPTIONAL MATCH " +
							"pMutualFriends=(me)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(me), " +
							"(users)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(users) " +
							"RETURN DISTINCT({name: users.name, email: users.email, length: 1, photo: users.photo, " +
							"qtde: count(DISTINCT pMutualFriends)}) as person " +
							"ORDER BY person.length, person.qtde DESC " +
							"UNION ALL " +
//							"MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
//							"(users:Person {typeOfAccount: 'CONTRACTOR'}), " +
//							"(users)-[:LIVES_IN]-(city)<-[:LIVES_IN]-(me) " +
//							"WHERE NOT((users)-[:WORKS_IN]->()<-[:WORKS_IN]-(me)) " +
//							"OPTIONAL MATCH " +
//							"pMutualFriends=(me)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(me), " +
//							"(users)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(users) " +
//							"RETURN DISTINCT(users.name), users.email, 2 as length, " +
//							"count(DISTINCT pMutualFriends) AS mutualFriends " +
//							"ORDER BY length, mutualFriends DESC \"}";
							"MATCH (me:Person {email: '" + person.getEmail() + "'}), " + 
							"(users:Person), " +
							"(users)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me) " +
							"WHERE NOT((users)-[:WORKS_IN]->()<-[:WORKS_IN]-(me)) " +
							"AND users.typeOfAccount <> 'SERVICE_PROVIDER' " +
							"AND NOT((users)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(users)) " +
							"OPTIONAL MATCH pMutualFriends=(me)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(me), " +
							"(users)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(users) " +
							"RETURN DISTINCT({name: users.name, email: users.email, length: 2, photo: users.photo, " +
							"qtde: count(DISTINCT pMutualFriends)}) as person " +
							"ORDER BY person.length, person.qtde DESC \"}";
		System.out.println(query);
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

	
	
	public String addPartner(Person me, String partnerEmail) {
		
		WebResource resource = FactoryDAO.GetInstance();
		Long currentTime = new Timestamp(new Date().getTime()).getTime();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + me.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}) " +
				"CREATE (me)-[:PARTNER_OF {since: " + currentTime + "}]->(partner) " +
				"RETURN me.name, me.email, partner.name, partner.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}


	
	public String cancelPartner(Person me, String partnerEmail) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + me.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}), " +
				"(partner)-[rel:PARTNER_OF]->(me) " +
				"DELETE rel " +
				"RETURN me.name, me.email, partner.name, partner.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		
		query = "{\"query\":\" MATCH (me:Person {email: '" + me.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}), " +
				"(me)-[rel:PARTNER_OF]->(partner) " +
				"DELETE rel " +
				"RETURN me.name, me.email, partner.name, partner.email; \"}";
		System.out.println(query);
		responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		objectCreate = responseCreate.getEntity(String.class);
		return objectCreate;
	}


	public JSONArray getAllRequestPartner(Person person) throws JSONException {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person), " +
				"(partner)-[:PARTNER_OF]->(me)" +
				"WHERE partner <> me AND NOT((me)-[:PARTNER_OF]->(partner)) " +
				"RETURN {name: partner.name, email: partner.email} as partner; \"}";
		System.out.println(query);
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
	
	public JSONArray getAllPartners(Person person) throws JSONException {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person), " +
				"(partner)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partner)" +
				"WHERE partner <> me " +
				"RETURN {name: partner.name, email: partner.email, photo: partner.photo} as partner; \"}";
		System.out.println(query);
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



	public JSONArray acceptPartnerRequest(Person person, String partnerEmail) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		Long currentTime = new Timestamp(new Date().getTime()).getTime();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}), " +
				"(partner)-[:PARTNER_OF]->(me) " +
				"CREATE (me)-[:PARTNER_OF {since: " + currentTime + "}]->(partner) " +
				"RETURN {myName: me.name, myEmail: me.email, partnerName: partner.name, partnerEmail: partner.email} as request; \"}";
		System.out.println(query);
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



	public JSONArray rejectPartnerRequest(Person person, String partnerEmail) throws JSONException {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}), " +
				"(partner)-[rel:PARTNER_OF]->(me) " +
				"DELETE rel " +
				"RETURN {myName: me.name, myEmail: me.email, partnerName: partner.name, partnerEmail: partner.email} as request; \"}";
		System.out.println(query);
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



	public String searchNewPartners(Person person, String partner) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(users:Person), " +
							"(users)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me) " +
							"WHERE users.name =~ '" + partner + ".*' " +
							"AND users.typeOfAccount <> 'SERVICE_PROVIDER' " +
							"AND users <> me AND NOT((users)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(users)) " + 
							"OPTIONAL MATCH " +
							"pMutualFriends=(me)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(me), " +
							"(users)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(users) " +
							"RETURN DISTINCT(users.name), users.email, 1 as length, users.photo, " +
							"count(DISTINCT pMutualFriends) AS mutualFriends " +
							"ORDER BY length, mutualFriends DESC " +
							"UNION ALL " +
							"MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(users:Person), " +
							"(users)-[:LIVES_IN]-(city)<-[:LIVES_IN]-(me) " +
							"WHERE users.name =~ '" + partner + ".*' " +
							"AND users.typeOfAccount <> 'SERVICE_PROVIDER' " +
							"AND NOT((users)-[:WORKS_IN]->()<-[:WORKS_IN]-(me)) " +
							"AND NOT((users)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(users)) " +
							"OPTIONAL MATCH " +
							"pMutualFriends=(me)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(me), " +
							"(users)-[:PARTNER_OF]->(another)-[:PARTNER_OF]->(users) " +
							"RETURN DISTINCT(users.name), users.email, 2 as length, users.photo, " +
							"count(DISTINCT pMutualFriends) AS mutualFriends " +
							"ORDER BY length, mutualFriends DESC \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}



	public String searchNewPartnersOnlyByName(Person person, String partner) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(users:Person), " +
							"(users)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me) " +
							"WHERE users.name =~ '" + partner + ".*' " +
							"AND users.typeOfAccount <> 'SERVICE_PROVIDER' " +
							"AND NOT((users)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(users)) " + 
							"AND users <> me " +
							"RETURN DISTINCT(users.name), users.email, 3 as length, users.photo " +
							"ORDER BY users.name ASC; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;

	}



	public boolean isMyPartner(Person person, String partner) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " + 
				"(partner:Person {email: '" + partner + "'}) " +
				"WHERE (me)-[:PARTNER_OF]->(partner) OR (partner)-[:PARTNER_OF]->(me) " +
				"RETURN COUNT(me) as qtde; \"}";
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



	public JSONArray getCommonsPartners(Person person, String partnerEmail) throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(partner:Person {email: '" + partnerEmail + "'}), " + 
							"(commons:Person), " +
							"(me)-[:PARTNER_OF]->(commons)-[:PARTNER_OF]->(me), " +
							"(partner)-[:PARTNER_OF]->(commons)-[:PARTNER_OF]->(partner) " +
							"WHERE me <> partner and me <> commons and partner <> commons " +
							"RETURN {name: commons.name, email: commons.email} as common " +
							"ORDER BY common.name ASC; \"}";
		System.out.println(query);
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
