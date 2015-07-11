package br.edu.univas.si.tcc.dao;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.univas.si.tcc.model.Company;

public class CompanyDAO {

	public boolean companyAlreadyExist(Company company) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (company:Company {name: '" + company.getName() + "'}), " +
				"(city:City {name: '" + company.getLocatedIn().getName() + "'}), " +
				"(uf:UF {name: '" + company.getLocatedIn().getUf().getName() + "'}), " +
				"(company)-[:LOCATED_IN]->(city), " +
				"(city)-[:BELONGS_TO]->(uf) " +
				"RETURN COUNT(company) as qtde; \"}";
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

	public void createCompany(Company company) {
		
		WebResource resource = FactoryDAO.GetInstance();
		
		String query = null;
		query = "{\"query\":\" MATCH (city:City {name: '" + company.getLocatedIn().getName() + "'}), " +
				"(uf:UF {name: '" + company.getLocatedIn().getUf().getName() + "'}) " + 
				"CREATE (company:Company {name: '" + company.getName() + "'}), " +
				"(company)-[:LOCATED_IN]->(city), " +
				"(city)-[:BELONGS_TO]->(uf); \"}";
		System.out.println(query);
		/* Corrigir a consulta para retornar um valor ou tratar quando vier null */ 
		
		ClientResponse responseCreate = resource.accept(MediaType.APPLICATION_JSON)
												.type(MediaType.APPLICATION_JSON).entity(query)
												.post(ClientResponse.class);
		responseCreate.getEntity(String.class);
	}
}
