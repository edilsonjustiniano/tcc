package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.model.Person;

public class RatingDAO {

	public String getLastestRatings(Person person) {
		
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
							"RETURN sp.name, service.name, executed.note, executed.comments, executed.date " +
							"ORDER BY executed.date ASC " +
							"LIMIT 4; \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
									.type(MediaType.APPLICATION_JSON).entity(query)
									.post(ClientResponse.class);
		String result = responseCreate.getEntity(String.class);
			
		return result;
	}
	
}
