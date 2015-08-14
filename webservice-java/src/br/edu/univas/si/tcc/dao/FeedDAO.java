package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.model.Person;

public class FeedDAO {

	public String getLastPartnership(Person person) {

		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
							"(partners:Person), " +
							"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners), " +
							"(partners)-[partnerA:PARTNER_OF]->(user)-[partnerB:PARTNER_OF]->(partners) " +
							"WHERE partners <> me " +
							"RETURN DISTINCT(partners), partners.name, user, user.name, partnerA.since, partnerB.since " +
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
							"(executed)-[:TO]->(partners), " +
							"(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partnerst <> 'CONTRACTOR) " +
							"WHERE partners <> me AND sp.typeOfAccoun' " +
							"RETURN DISTINCT(executed), executed.note, partners.name, executed.comments, executed.date " +
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
