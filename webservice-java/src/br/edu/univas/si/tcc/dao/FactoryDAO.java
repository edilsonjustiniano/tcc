package br.edu.univas.si.tcc.dao;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class FactoryDAO {

	private static final String DATABASE_ENDPOINT = "http://localhost:7474/db/data";

	private static final String DATABASE_USERNAME = "neo4j";

	private static final String DATABASE_PASSWORD = "123";

	private static final String cypherUrl = DATABASE_ENDPOINT + "/cypher";
    
	private static WebResource instance;
	
	private FactoryDAO() {
	}
	
	public static WebResource GetInstance() {
		WebResource resource = null;
		if (instance == null) {
			Client c = Client.create();
			c.addFilter(new HTTPBasicAuthFilter(DATABASE_USERNAME, DATABASE_PASSWORD));
			resource = c.resource(cypherUrl);
		}
		return resource;
		
	}

}
