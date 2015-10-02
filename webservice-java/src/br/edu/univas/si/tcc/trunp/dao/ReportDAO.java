package br.edu.univas.si.tcc.trunp.dao;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.util.JSONUtil;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ReportDAO {

	public JSONArray lastEvaluateOfServiceProvider(String user,
			String serviceProvider, String service, int limit) throws JSONException {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "MATCH (me:Person {email: '"+ user +"'}), " + 
				"(sp:Person {email: '"+ serviceProvider +"'}), "  +
				"(service:Service {name: '"+ service +"'}), " +
				"(executed:Execute), " +
				"(partners:Person), "  +
				"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
				"(sp)-[:PROVIDE]->(service), " +
				"(service)-[:EXECUTE]->(executed), " +
				"(sp)-[:EXECUTE]->(executed)," +
				"(executed)-[:TO]->(partners)" +
				"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
				"RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, " +
				"date: executed.date}) as execution ORDER BY execution.note DESC LIMIT " + limit;

		String request = "{\"query\":\"" + query + "\"}";
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(request)
				.post(ClientResponse.class);

		String resp = response.getEntity(String.class);

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

	public JSONArray lastEvaluateOfServiceInNetwork(String user,
			String serviceProvider, String service, int limit) throws JSONException {
			WebResource resource = FactoryDAO.GetInstance();

		String query = "MATCH (me:Person {email: '"+ user +"'}), " + 
				"(sp:Person {email: '"+ serviceProvider +"'}),(otherSP:Person), "  +
				"(service:Service {name: '"+ service +"'}), " +
				"(executed:Execute), " +
				"(partners:Person), "  +
				"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
				"(otherSP)-[:PROVIDE]->(service), " +
				"(service)-[:EXECUTE]->(executed), " +
				"(otherSP)-[:EXECUTE]->(executed)," +
				"(executed)-[:TO]->(partners)" +
				"WHERE otherSP.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
				"AND sp <> otherSP " +
				"RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, " +
				"date: executed.date, to: otherSP.name}) as execution ORDER BY execution.date DESC LIMIT " + limit;

		String request = "{\"query\":\"" + query + "\"}";
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(request)
				.post(ClientResponse.class);

		String resp = response.getEntity(String.class);

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
