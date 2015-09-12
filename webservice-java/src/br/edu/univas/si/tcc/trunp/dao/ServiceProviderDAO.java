package br.edu.univas.si.tcc.trunp.dao;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

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

	
	
	/*
	 * We will implement three different way to find the person by services.
	 * The first one will be done by my network partners and after that will be done
	 * by my company and last one will be my city
	 */
	public JSONArray getServiceProvidersByService(Person person, String service) throws JSONException {
		WebResource resource = FactoryDAO.GetInstance();		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), "+
				"(sp:Person), " +
				"(service:Service), " + 
				"(executed:Execute), " + 
				"(partners:Person), " + 
				"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
				"(sp)-[:PROVIDE]->(service), " +
				"(service)-[:EXECUTE]->(executed), " +
				"(sp)-[:EXECUTE]->(executed) " +
				"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
				"AND UPPER(service.name) = UPPER('" + service + "') " +
				"AND (executed)-[:TO]->(partners) " +
				"AND me <> sp " +
				"RETURN DISTINCT({serviceProviderName: sp.name, serviceProviderEmail: sp.email, service: service.name, " +
				"total: count(executed), media: avg(executed.note), order: 1}) as sp ORDER BY sp.order, sp.media DESC " +
				"UNION ALL " +
				"MATCH (me:Person {email: '" + person.getEmail() + "'}), " +  
				"(sp:Person), " +
				"(service:Service {name: '" + service + "'}), " + 
				"(executed:Execute), " +
				"(partners:Person), " +
				"(partners)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me), " +
				"(sp)-[:PROVIDE]->(service), " + 
				"(service)-[:EXECUTE]->(executed), " + 
				"(sp)-[:EXECUTE]->(executed), " +
				"(executed)-[:TO]->(partners) " +
				"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
				"AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners)) " +
				"AND me <> sp " +
				"RETURN DISTINCT({serviceProviderName: sp.name, serviceProviderEmail: sp.email, service: service.name, " +
				"total: count(executed), media: avg(executed.note), order: 2}) as sp ORDER BY sp.order, sp.media DESC " +
				"UNION ALL " +
				"MATCH (me:Person {email: '" + person.getEmail() + "'}), " +  
				"(sp:Person), " +
				"(service:Service {name: '" + service + "'}), " + 
				"(executed:Execute), " + 
				"(partners:Person), " +
				"(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me), " +
				"(sp)-[:PROVIDE]->(service), " + 
				"(service)-[:EXECUTE]->(executed), " + 
				"(sp)-[:EXECUTE]->(executed), " +
				"(executed)-[:TO]->(partners) " +
				"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
				"AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners)) " +
				"AND NOT((partners)-[:WORKS_IN]->()<-[:WORKS_IN]-(me)) " +
				"AND me <> sp " +
				"RETURN DISTINCT({serviceProviderName: sp.name, serviceProviderEmail: sp.email, service: service.name, " +
				"total: count(executed), media: avg(executed.note), order: 3}) as sp ORDER BY sp.order, sp.media DESC " + 
				"UNION ALL " +
				"MATCH (me:Person {email: '" + person.getEmail() + "'}), " +  
				"(sp:Person), " +
				"(service:Service {name: '" + service + "'}), " +
				"(partners:Person), " +
				"(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me), " +
				"(sp)-[:PROVIDE]->(service) " +
				"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
				"AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners)) " +
				"AND NOT((partners)-[:WORKS_IN]->()<-[:WORKS_IN]-(me)) " +
				"AND me <> sp " +
				"RETURN DISTINCT({serviceProviderName: sp.name, serviceProviderEmail: sp.email, service: service.name, total: 0, "+
				"media: 0, order: 4}) as sp ORDER BY sp.order, sp.media DESC; \"}";
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
	
	

	public JSONArray getServiceProviderData(String providerEmail, String providerService) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (sp:Person {email: '" + providerEmail + "'}), " + 
							"(service:Service {name: '" + providerService + "'}), " +
							"(sp)-[:PROVIDE]->(service) " +
							"WHERE sp.typeOfAccount <> 'CONTRACTOR' " + 
							"RETURN DISTINCT({serviceProviderName: sp.name, serviceProviderEmail: sp.email, " +
							"gender: sp.gender, photo: sp.photo, service: service.name}) as person \"}";
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


	public JSONArray getServiceProviderRatingInMyNetworkPartners(Person person,
			String service, String serviceProvider) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		
//		MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}),  
//		(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}), 
//		(service:Service), 
//		(executed:Execute), 
//		(partners:Person {typeOfAccount: 'CONTRACTOR'}), 
//		(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners),
//		(sp)-[:PROVIDE]->(service), 
//		(service)-[:EXECUTE]->(executed), 
//		(sp)-[:EXECUTE]->(executed) 
//		WHERE UPPER(service.name) = UPPER('doméstica') 
//		AND (executed)-[:TO]->(partners)
//		RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, count(executed) as total, avg(executed.note) as media, 1 as order ORDER BY order, media DESC
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
						"(sp:Person {email: '" + serviceProvider +  "'}), " + 
						"(service:Service {name: '" + service + "'}), " +
						"(executed:Execute), " +
						"(partners:Person), " +
						"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " + 
						"(sp)-[:PROVIDE]->(service), " + 
						"(service)-[:EXECUTE]->(executed), " +
						"(sp)-[:EXECUTE]->(executed), " +
						"(executed)-[:TO]->(partners) " +
						"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
						"AND UPPER(service.name) = UPPER('" + service + "') " +
						"RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, date: executed.date}) as execution ORDER BY execution.note DESC; \"}";

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


	public JSONArray getServiceProviderRatingInMyCompany(Person person,
			String service, String serviceProvider) throws JSONException {
	
		WebResource resource = FactoryDAO.GetInstance();
		
//		MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}),  
//		(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}),
//		(service:Service {name: 'Doméstica'}), 
//		(executed:Execute), 
//		(partners:Person {typeOfAccount: 'CONTRACTOR'}),
//		(partners)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me),
//		(sp)-[:PROVIDE]->(service), 
//		(service)-[:EXECUTE]->(executed), 
//		(sp)-[:EXECUTE]->(executed),
//		(executed)-[:TO]->(partners)
//		WHERE NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners))
//		RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, count(executed) as total, avg(executed.note) as media, 2 as order ORDER BY order, media DESC
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
						"(sp:Person {email: '" + serviceProvider +  "'}), " + 
						"(service:Service {name: '" + service + "'}), " +
						"(executed:Execute), " +
						"(partners:Person), " +
						"(partners)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me), " + 
						"(sp)-[:PROVIDE]->(service), " + 
						"(service)-[:EXECUTE]->(executed), " +
						"(sp)-[:EXECUTE]->(executed), " +
						"(executed)-[:TO]->(partners) " +
						"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
						"AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners)) " +
						"RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, date: executed.date}) as execution ORDER BY execution.note DESC; \"}";

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



	public JSONArray getServiceProviderRatingInMyCity(Person person,
			String service, String serviceProvider) throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();
		
//		MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}),  
//		(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}),  //remover o email para pegar todos os provedores de serviços 
//		(service:Service {name: 'Doméstica'}), 
//		(executed:Execute), 
//		(partners:Person {typeOfAccount: 'CONTRACTOR'}),
//		(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me),
//		(sp)-[:PROVIDE]->(service), 
//		(service)-[:EXECUTE]->(executed), 
//		(sp)-[:EXECUTE]->(executed),
//		(executed)-[:TO]->(partners)
//		WHERE NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners))
//		AND NOT((partners)-[:WORKS_IN]->()<-[:WORKS_IN]-(me))
//		RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, count(executed) as total, avg(executed.note) as media, 3 as order ORDER BY order, media DESC
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
						"(sp:Person {email: '" + serviceProvider +  "'}), " + 
						"(service:Service {name: '" + service + "'}), " +
						"(executed:Execute), " +
						"(partners:Person), " +
						"(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me), " + 
						"(sp)-[:PROVIDE]->(service), " + 
						"(service)-[:EXECUTE]->(executed), " +
						"(sp)-[:EXECUTE]->(executed), " +
						"(executed)-[:TO]->(partners) " +
						"WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' " +
						"AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners)) " +
						"AND NOT((partners)-[:WORKS_IN]->()<-[:WORKS_IN]-(me)) " +
						"RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, date: executed.date}) as execution ORDER BY execution.note DESC; \"}";

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



	public boolean isNewService(String service) {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (service:Service) " +
					"WHERE UPPER(service.name)= UPPER('"+ service +"') " +
					"RETURN COUNT(service) as qtde; \"}";
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

	
	//checar se o servico jao foi atribuido ao provedor de servicos antes
	public boolean isAlreadyStored(String service, Person person) {

		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
					"(service:Service), " +
					"(me)-[:PROVIDE]->(service) " +
					"WHERE UPPER(service.name) = UPPER('" + service + "') " +
					"RETURN COUNT(service) as qtde; \"}";
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


	
	// Atribuindo o serviço a uma pessoa
	public JSONArray addService(String service, Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		String query = "{\"query\":\" MATCH (n:Person {email: '"+ person.getEmail() +"'}), "
				+ "(service:Service {name: '"+ service +"'}) " 
				+ "WITH DISTINCT (n), service "
				+ "CREATE (n)-[:PROVIDE]->(service) "
				+ "RETURN {email: n.email, service: service.name} as result; \"}";
		
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



	public JSONArray getMyServices(Person person) throws JSONException {
		
		WebResource resource = FactoryDAO.GetInstance();
		String query = "{\"query\":\" MATCH (me:Person {email: '"+ person.getEmail() +"'}), "
				+ "(service:Service), " 
				+ "(me)-[:PROVIDE]->(service) "
				+ "WHERE me.typeOfAccount <> 'CONTRACTOR' "
				+ "RETURN DISTINCT({service: service.name}) as s; \"}";
		
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



	public JSONArray removeService(String service, Person person) throws JSONException {
		WebResource resource = FactoryDAO.GetInstance();
		String query = "{\"query\":\" MATCH (me:Person {email: '"+ person.getEmail() +"'}), "
				+ "(service:Service), " 
				+ "(me)-[relProvide:PROVIDE]->(service) "
				+ "WHERE me.typeOfAccount <> 'CONTRACTOR' AND service.name = '" + service + "' "
				+ "DELETE relProvide "
				+ "RETURN DISTINCT({service: service.name}) as s; \"}";
		
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
