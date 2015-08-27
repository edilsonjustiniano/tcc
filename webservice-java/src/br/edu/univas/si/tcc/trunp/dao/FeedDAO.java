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

public class FeedDAO {

	public JSONArray getLastestPartnership(Person person) throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(partners:Person), " +
							"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
							"(partners)-[partnerA:PARTNER_OF]->(user)-[partnerB:PARTNER_OF]->(partners) " +
							"WHERE partners <> me " +
							"RETURN DISTINCT({partnerName: partners.name, partnerEmail: partners.email, " +
							"userName: user.name, userEmail: user.email, partnerASince: partnerA.since, " + 
							"partnerBSince: partnerB.since, null1: 0, null2: 0, rating: false}) as partnership " +
							"ORDER BY partnership.partnerASince, partnership.partnerBSince DESC \"}";
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
	
	
	
	public JSONArray getLastestRatings(Person person) throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(partners:Person), " +
							"(sp:Person), " +
							"(service:Service), " +
							"(executed:Execute), " +
							"(sp)-[:PROVIDE]->(service), " +
							"(service)-[:EXECUTE]->(executed), " +
							"(sp)-[:EXECUTE]->(executed), " +
							"(executed)-[:TO]->(partners), " +
							"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners) " +
							"WHERE partners <> me AND sp.typeOfAccount <> 'CONTRACTOR' " +
							"RETURN DISTINCT({serviceProviderName: sp.name, serviceProviderEmail: sp.email, " +
							"service: service.name, note: executed.note, comments: executed.comments, " +
							"date: executed.date, partnerName: partners.name, partnerEmail: partners.email, rating: true}) as rating " +
							"ORDER BY rating.date DESC; \"}";
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
