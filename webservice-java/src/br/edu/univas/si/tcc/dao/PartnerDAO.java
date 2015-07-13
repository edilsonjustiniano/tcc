package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.model.Person;

public class PartnerDAO {

	public String getPossiblePartners(Person person) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (cityWorks:City {name: '" + person.getWorksIn().getLocatedIn().getName() + "'}), " +
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
		System.out.println(query);
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		String objectCreate = responseCreate.getEntity(String.class);
		
		return objectCreate;
	}

}
