package br.edu.univas.si.tcc.resources;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.controller.TokenController;
import br.edu.univas.si.tcc.dao.CompanyDAO;
import br.edu.univas.si.tcc.dao.PersonDAO;
import br.edu.univas.si.tcc.dao.TokenDAO;
import br.edu.univas.si.tcc.model.City;
import br.edu.univas.si.tcc.model.Company;
import br.edu.univas.si.tcc.model.Person;
import br.edu.univas.si.tcc.model.Token;
import br.edu.univas.si.tcc.model.UF;
import br.edu.univas.si.tcc.util.Base64Util;

import com.sun.jersey.core.header.FormDataContentDisposition;

@Path("/person")
public class PersonResource {
	
	private PersonDAO dao = new PersonDAO();
	private TokenDAO tokenDao; 
	private CompanyDAO companyDao;

	@Path("/createAccountPersonalData")
	@POST
	@Produces("application/json")
	public String createAccountPersonalData(String json) {
		System.out.println(json);
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(json);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		/* Check if the email is free or not */
		try {
			if (!dao.isFreeEmail(jsonObj.getString("email"))) {
				JSONObject response = null;
				response = new JSONObject();
				response.put("success", false);
				response.put("mesage", "Email já registrado, por favor utilize outro!");
				return response.toString();
			}
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		
		String objectCreate = dao.createAccountPersonalData(jsonObj);
		
		JSONObject response = null;
		try {
			response = new JSONObject(objectCreate);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			response.put("success", true);
			response.put("mesage", "Sucesso ao cadastrar a nova conta!");
			
//			byte[] token = Base64.encode(jsonObj.getString("email") + "||" + jsonObj.getString("password"));
			byte[] token = Base64Util.encodeToken(jsonObj.getString("email"), jsonObj.getString("password"));
			System.out.println("encodedBytes " + new String(token));
			
//			byte[] decodedBytes = Base64.decode(token);
			Token tokenDecoded = Base64Util.decodeToken(token);
			System.out.println("decodedBytes " + tokenDecoded.getEmail() + Base64Util.BASE64_TOKEN_SEPARATOR + tokenDecoded.getPassword() + Base64Util.BASE64_TOKEN_SEPARATOR + tokenDecoded.getLastAccessTime());
			
			response.put("token", new String(token));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	
	
	@Path("/createAccountWorkData")
	@POST
	@Produces("application/json")
	public String createAccountWorkData(String json) {
		
		System.out.println(json);
		JSONObject jsonObj = null;
		JSONObject response = null;
		String token = null;
		Token tokenDecoded = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		//Check if session is Valid
		if (token == null || token.equals("")) {
			response = new JSONObject();
			try {
				response.put("success", false);
				response.put("mesage", "É necessário informar os dados pessoais primeiro!");
				
				return response.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		} else { //token is filled
			
			tokenDecoded = Base64Util.decodeToken(token.getBytes());
			
			//Check if the session is expired
			TokenController tokenController = new TokenController();
			if (tokenController.isExpiredSession(tokenDecoded)) {
				response = new JSONObject();
				try {
					response.put("success", false);
					response.put("mesage", "Sessão expirada!");
					response.put("sessionExpired", true);
					
					return response.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			
			tokenDao = new TokenDAO();
			
			if (!tokenDao.isValidSession(tokenDecoded)) {
				response = new JSONObject();
				try {
					response.put("success", false);
					response.put("mesage", "Sessão inválida!");
					response.put("sessionExpired", false);
					
					return response.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//Check if there is a company with the same name informed by user and in the same city
		JSONObject jsonWorksIn = null;
		JSONObject jsonLocatedIn = null;
		JSONObject jsonStateOfWork = null;
		Company company = null;
		City city = null;
		UF uf = null;
		try {
			jsonObj = new JSONObject(json);
			String worksIn = jsonObj.getString("worksIn"); //{"name":"CEP","locatedIn":{"name":"ITAJUBÁ"}}
			jsonWorksIn = new JSONObject(worksIn);//{"name":"CEP","locatedIn":{"name":"ITAJUBÁ","state":{"name":"MINAS GERAIS"}}}
			jsonLocatedIn = new JSONObject(jsonWorksIn.getString("locatedIn")); //{"name":"ITAJUBÁ","state":{"name":"MINAS GERAIS"}}
			jsonStateOfWork = new JSONObject(jsonLocatedIn.getString("state"));//{"name":"MINAS GERAIS"}
			
			company = new Company();
			company.setName(jsonWorksIn.getString("name"));
			city = new City();
			city.setName(jsonLocatedIn.getString("name"));
			uf = new UF();
			uf.setName(jsonStateOfWork.getString("name"));
			city.setUf(uf);
			company.setLocatedIn(city);
			
			companyDao = new CompanyDAO();
			if (!companyDao.companyAlreadyExist(company)) { //create node for company because it is not exist in graph
				
				companyDao.createCompany(company);
			}
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		//Now we need to edit the user data on database
		Person person = new Person();
		person.setEmail(tokenDecoded.getEmail()); //set original e-mail from user
		person.setWorksIn(company);
		
		JSONObject jsonLivesIn = null;
		JSONObject jsonState = null;
		City cityLives = new City();
		UF ufLives = new UF();
		
		try {
			jsonObj = new JSONObject(json);
			String livesIn = jsonObj.getString("livesIn"); //{"name":"Itajubá","state":{"name":"Minas Gerais"}},"worksIn":{"name":"CEP1","locatedIn":{"name":"Itajubá","state":{"name":"Minas Gerais"}}},"district":"Centro","address":"Rua Nova, 100","token":"cGVkcm9AZ21haWwuY29tfHBlZHJv"}
			jsonLivesIn = new JSONObject(livesIn);
			jsonState = new JSONObject(jsonLivesIn.getString("state")); //{"name":"Minas Gerais"}
			
			cityLives.setName(jsonLivesIn.getString("name"));
			ufLives.setName(jsonState.getString("name"));
			cityLives.setUf(ufLives);
			
			person.setLivesIn(cityLives);
			person.setAddress(jsonObj.getString("address"));
			person.setDistrict(jsonObj.getString("district"));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		String objCreated = dao.createAccountWorkData(person);
		byte[] tokenUpdated = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());

		try {
			response = new JSONObject(objCreated);
			response.put("success", true);
			response.put("mesage", "Sucesso ao editar a nova conta!");
			response.put("token", new String(tokenUpdated));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		return response.toString();
		/* 1 -> Firstly, check if the session is valid, case the session is not valid, we do not need to do anything. Otherwise, continue.
		 * 2 -> Check if there is a company in the same city with the same nome, before to cadastre a new company.
		 * 3 -> And last, edit the user data on database
		 * Check if there is a valid session, informed by user and get the node
		 * regarding him
		 */
	}
	
}
