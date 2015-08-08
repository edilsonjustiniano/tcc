package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.model.Person;

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

	
	public String getServiceProvidersByService(Person person, String service) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		/*
		 * /* Na minha rede de parceiros */
MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}),  
(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}), 
(service:Service), 
(executed:Execute), 
(partners:Person {typeOfAccount: 'CONTRACTOR'}), 
(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), 
(partners)-[:PARTNER_OF]->(user)-[:PARTNER_OF]->(partners), 
(sp)-[:PROVIDE]->(service), 
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed) 
WHERE UPPER(service.name) = UPPER('doméstica') 
AND ((executed)-[:TO]->(user) OR (executed)-[:TO]->(partners)) 
RETURN sp.name, sp.email, service.name, count(executed) as total, avg(executed.note) as media ORDER BY media DESC;


/* Avaliação de Pessoas da minha cidade */
MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}),  
(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}),  //remover o email para pegar todos os provedores de serviços 
(service:Service {name: 'Doméstica'}), 
(executed:Execute), 
(partners:Person {typeOfAccount: 'CONTRACTOR'}),
(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me),
(sp)-[:PROVIDE]->(service), 
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed),
(executed)-[:TO]->(partners)
RETURN partners.name, sp.name, sp.email, service.name, count(executed) as total, avg(executed.note) as media ORDER BY media DESC;



/* Avaliação de Pessoas da minha empresa */
MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}),  
(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}), //remover o email para pegar todos os provedores de serviços 
(service:Service {name: 'Doméstica'}), 
(executed:Execute), 
(partners:Person {typeOfAccount: 'CONTRACTOR'}),
(partners)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me),
(sp)-[:PROVIDE]->(service), 
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed),
(executed)-[:TO]->(partners)
RETURN partners.name, sp.name, sp.email, service.name, count(executed) as total, avg(executed.note) as media ORDER BY media DESC;

		 */
		
//		MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'joao1@gmail.com'}), 
//		(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}),
//		(service:Service),
//		(executed:Execute),
//		(partners:Person {typeOfAccount: 'CONTRACTOR'}),
//		(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners),
//		(partners)-[:PARTNER_OF]->(user)-[:PARTNER_OF]->(partners),
//		(sp)-[:PROVIDE]->(service),
//		(service)-[:EXECUTE]->(executed),
//		(sp)-[:EXECUTE]->(executed)
//
//		WHERE UPPER(service.name) = UPPER('Doméstica') AND ((executed)-[:TO]->(user) OR (executed)-[:TO]->(partners))
//		RETURN sp.name, sp, avg(executed.note) as media, count(executed) as total_person_evaluate;

//		OLD RETURN ALL SERVICES PROVIDERS
//		String query = "{\"query\":\" MATCH (sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}), (service: Service), " + 
//						"(sp)-[:PROVIDE]->(service) " +
//						"WHERE service.name = '" + service.trim() + "' " + 
//						"RETURN DISTINCT(sp.name), sp.email, service.name LIMIT 10; \"}";
		String query = "{\"query\":\" MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: '" + person.getEmail() + "'}),  " +
						"(sp:Person {typeOfAccount: 'SERVICE_PROVIDER'}), " + 
						"(service:Service), " +
						"(executed:Execute), " +
						"(partners:Person {typeOfAccount: 'CONTRACTOR'}), " +
						"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
						"(partners)-[:PARTNER_OF]->(user)-[:PARTNER_OF]->(partners), " +
						"(sp)-[:PROVIDE]->(service), " +
						"(service)-[:EXECUTE]->(executed), " +
						"(sp)-[:EXECUTE]->(executed) " +
						"WHERE UPPER(service.name) = UPPER('" + service + "') " + 
						"AND ((executed)-[:TO]->(user) OR (executed)-[:TO]->(partners)) " + 
						"RETURN sp.name, sp.email, service.name, count(executed) as totalPersonEvaluate, avg(executed.note) as media ORDER BY media DESC; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}


	public String getServiceProviderData(String providerEmail, String providerService) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: '" + providerEmail + "'}), " + 
							"(service:Service {name: '" + providerService + "'}), " +
							"(sp)-[:PROVIDE]->(service) " +
							"RETURN DISTINCT(sp.name), sp.email, sp.gender, service.name \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;

	}


	public boolean isNewEvaluate(String providerEmail, String providerService, Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
//		MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: 'edilsonjustiniano@gmail.com'}), 
//		(sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: 'mariaaparecida@gmail.com'}),
//		(service:Service {name: 'Doméstica'}),
//		(service)-[:EXECUTE]->(executed),
//		(sp)-[:EXECUTE]->(executed)-[:TO]->(me)
//		RETURN service.name, me.name, sp.name, COUNT(executed) as qtde;
		
		String query = "{\"query\":\" MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: '" + person.getEmail() + "'}), " +
					"(sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: '" + providerEmail + "'}), " + 
					"(service:Service {name: '" + providerService + "'}), " +
					"(service)-[:EXECUTE]->(executed), " +
					"(sp)-[:EXECUTE]->(executed)-[:TO]->(me) " + 
					"RETURN COUNT(executed) as qtde; \"}";
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


	public String saveEvaluate(String providerEmail, String providerService, int note, String comments, Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();

		String query = "{\"query\":\" MATCH (me:Person {typeOfAccount: 'CONTRACTOR', email: '" + person.getEmail() + "'}), " +
					"(sp:Person {typeOfAccount: 'SERVICE_PROVIDER', email: '" + providerEmail + "'}), " + 
					"(service:Service {name: '" + providerService + "'}) " +
					"CREATE (executed:Execute {note: " + note + ", comments: '" + comments + "'}), " +
					"(service)-[:EXECUTE]->(executed), " +
					"(sp)-[:EXECUTE]->(executed)-[:TO]->(me) " + 
					"RETURN service.name, me.name, sp.name, COUNT(executed) as qtde; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
			
	}
	
	

}
