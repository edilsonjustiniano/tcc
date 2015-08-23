package br.edu.univas.si.tcc.trunp.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.trunp.model.Person;

public class FeedDAO {

	public String getLastPartnership(Person person) {

		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(partners:Person), " +
							"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
							"(partners)-[partnerA:PARTNER_OF]->(user)-[partnerB:PARTNER_OF]->(partners) " +
							"WHERE partners <> me " +
							"RETURN DISTINCT(partners.name), partners.email, user.name, user.email, partnerA.since, partnerB.since, 0 as null1, 0 as null2, false as rating " +
							"ORDER BY partnerA.since, partnerB.since DESC \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
									.type(MediaType.APPLICATION_JSON).entity(query)
									.post(ClientResponse.class);
		String result = responseCreate.getEntity(String.class);
			
		return result;
	}
	
	
	
	public String getLastestRatings(Person person) {

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
							"RETURN DISTINCT(sp.name), sp.email, service.name, executed.note, executed.comments, executed.date, partners.name, partners.email, true as rating " +
							"ORDER BY executed.date DESC; \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
									.type(MediaType.APPLICATION_JSON).entity(query)
									.post(ClientResponse.class);
		String result = responseCreate.getEntity(String.class);
			
		return result;
	}

}
