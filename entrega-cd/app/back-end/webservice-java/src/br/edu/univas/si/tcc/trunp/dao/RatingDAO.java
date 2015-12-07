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

public class RatingDAO {

	public JSONArray getMyLastestRatings(Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(sp:Person), " +
							"(service:Service), " +
							"(executed:Execute), " +
							"(sp)-[:PROVIDE]->(service), " +
							"(service)-[:EXECUTE]->(executed), " +
							"(sp)-[:EXECUTE]->(executed), " +
							"(executed)-[:TO]->(me) " +
							"WHERE me.typeOfAccount <> 'SERVICE_PROVIDER' " +
							"AND sp.typeOfAccount <> 'CONTRACTOR' " +
							"RETURN {serviceProviderName: sp.name, serviceProviderEmail: sp.email, serviceProviderPhoto: sp.photo, service: service.name, note: executed.note, comments: executed.comments, date: executed.date} as rate " +
							"ORDER BY executed.date ASC " +
							"LIMIT 4; \"}";
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
	
	
	public boolean isNewEvaluate(String providerEmail, String providerService, Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
					"(sp:Person {email: '" + providerEmail + "'}), " + 
					"(service:Service {name: '" + providerService + "'}), " +
					"(service)-[:EXECUTE]->(executed), " +
					"(sp)-[:EXECUTE]->(executed)-[:TO]->(me) " +
					"WHERE sp.typeOfAccount <> 'CONTRACTOR' " +
					"RETURN COUNT(executed) as qtde; \"}";
		System.out.println(query);
		
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

	
	
	public JSONArray saveRating(String providerEmail, String providerService, int note, String comments, Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		Date currentTime = new Timestamp(new Date().getTime());
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
					"(sp:Person {email: '" + providerEmail + "'}), " + 
					"(service:Service {name: '" + providerService + "'}) " +
					"WHERE sp.typeOfAccount <> 'CONTRACTOR' " +
					"CREATE (executed:Execute {note: " + note + ", comments: '" + comments + "', date: '" + currentTime + "'}), " +
					"(service)-[:EXECUTE]->(executed), " +
					"(sp)-[:EXECUTE]->(executed)-[:TO]->(me) " +
					"RETURN {service: service.name, myName: me.name, serviceProviderName: sp.name, qtde: COUNT(executed)} as rating; \"}";
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
