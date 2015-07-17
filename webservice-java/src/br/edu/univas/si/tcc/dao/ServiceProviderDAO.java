package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.model.Person;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.Token;


public class ServiceProviderDAO {
	
 public String setAllService() {
	 JSONObject jsonObj = null;
	  Token tokenDecoded = null;
	  Person person = new Person();
	  
	  byte[] token = null;
	  
	  try {
	   jsonObj = new JSONObject(json);
	   token = jsonObj.getString("token").getBytes();
	  } catch (JSONException e1) {
	   e1.printStackTrace();
	  }
	  
	  tokenDecoded = Base64Util.decodeToken(token);
	  person.setEmail(tokenDecoded.getEmail());
	  person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
	
	WebResource resource = FactoryDAO.GetInstance();
	
    String query = "MATCH (eu:Person {email: '" + person.getEmail() + "'}), (service:Service {name: '" + service.getName() + "'}),";
    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON).entity(request)
			.post(ClientResponse.class);

    
    String request = query = "{\"query\":\" CREATE CREATE (eu)-[:PROVIDE]->(service) RETURN eu.name, eu.email, service.name,";
    
    String responseStr = response.getEntity(String.class);
	
	return responseStr;
}

}
