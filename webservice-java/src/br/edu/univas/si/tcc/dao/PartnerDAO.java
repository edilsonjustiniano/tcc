package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.model.Person;

public class PartnerDAO {

	public String getPossiblePartners(Person person) {
		
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
		query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), (users:Person), " +
				"(users)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me) " +
				"WHERE users <> me AND (NOT( (me-[:PARTNER_OF]->users) OR (users-[:PARTNER_OF]->me)))" + 
				"RETURN DISTINCT(users.name), users.email, company.name, 1 as length; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}

	
	
	public String addPartner(Person me, String partnerEmail) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + me.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}) " +
				"CREATE (me)-[:PARTNER_OF]->(partner) " +
				"RETURN me.name, me.email, partner.name, partner.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}



	public String getAllRequestPartner(Person person) {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person), " +
				"(partner)-[:PARTNER_OF]->(me)" +
				"WHERE partner <> me AND NOT((me)-[:PARTNER_OF]->(partner)) " +
				"RETURN partner.name, partner.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}



	public String acceptPartnerRequest(Person person, String partnerEmail) {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}), " +
				"(partner)-[:PARTNER_OF]->(me) " +
				"CREATE (me)-[:PARTNER_OF]->(partner) " +
				"RETURN me.name, me.email, partner.name, partner.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;

	}



	public String rejectPartnerRequest(Person person, String partnerEmail) {
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = "{\"query\":\" MATCH (me:Person {email: '" + person.getEmail() + "'}), " +
				"(partner:Person {email: '" + partnerEmail +  "'}), " +
				"(partner)-[rel:PARTNER_OF]->(me) " +
				"DELETE rel " +
				"RETURN me.name, me.email, partner.name, partner.email; \"}";
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;

	}
}
