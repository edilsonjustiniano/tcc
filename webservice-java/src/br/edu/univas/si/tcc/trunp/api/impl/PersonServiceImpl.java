package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.PersonService;
import br.edu.univas.si.tcc.trunp.controller.CompanyController;
import br.edu.univas.si.tcc.trunp.controller.PersonController;
import br.edu.univas.si.tcc.trunp.controller.TokenController;
import br.edu.univas.si.tcc.trunp.dao.CompanyDAO;
import br.edu.univas.si.tcc.trunp.dao.TokenDAO;
import br.edu.univas.si.tcc.trunp.model.City;
import br.edu.univas.si.tcc.trunp.model.Company;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.model.UF;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonServiceImpl implements PersonService {

	private PersonController personController = new PersonController();
	private TokenController tokenController = new TokenController();
	private CompanyController companyController = new CompanyController();
	
	@POST
	@Path("/createaccount/personaldata")
	public JSONObject accountPersonalData(String data) throws JSONException {
		
		System.out.println(data);
		JSONObject jsonData = new JSONObject(data);
		
		/* Check if the email is free or not */
		if (!personController.isFreeEmail(jsonData.getString("email"))) {
			return JSONUtil.generateJSONError(false, "Email já registrado, por favor utilize outro!");
		}
		
		JSONArray objectCreate = personController.createAccountPersonalData(jsonData);
		
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, "Sucesso ao cadastrar a nova conta!", objectCreate); // null;
			
		byte[] token = Base64Util.encodeToken(jsonData.getString("email"), jsonData.getString("password"));
		System.out.println("encodedBytes " + new String(token));
			
		Token tokenDecoded = Base64Util.decodeToken(token);
		System.out.println("decodedBytes " + tokenDecoded.getEmail() + Base64Util.BASE64_TOKEN_SEPARATOR + tokenDecoded.getPassword() + Base64Util.BASE64_TOKEN_SEPARATOR + tokenDecoded.getLastAccessTime());
			
		json.put("token", new String(token));
			
		return json;
	}

	
	
	@POST
	@Path("/createaccount/workdata")
	public JSONObject accountWorkData(String data) throws JSONException {
		
		System.out.println(data);
		JSONObject jsonData = new JSONObject(data);
		JSONObject json = null;
		String token = null;
		Token tokenDecoded = null;
		token = jsonData.getString("token");
		
		//Check if session is Valid
		if (!tokenController.isValidToken(token)) {
			
			return JSONUtil.generateJSONError(false, "É necessário informar os dados pessoais primeiro!");
		
		} else { //token is filled

			tokenDecoded = Base64Util.decodeToken(token.getBytes());
			
			//Check if the session is expired
			TokenController tokenController = new TokenController();
			if (tokenController.isExpiredSession(tokenDecoded)) {
				return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada", true);
			}
			
			if (!tokenController.isValidSession(tokenDecoded)) {
				return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão Inválida!", false);
			}
		}
		
		
		//Check if there is a company with the same name informed by user and in the same city
		JSONObject jsonWorksIn = null;
		JSONObject jsonLocatedIn = null;
		JSONObject jsonStateOfWork = null;
		Company company = null;
		City city = null;
		UF uf = null;
		
		jsonData = new JSONObject(data);
		String worksIn = jsonData.getString("worksIn"); //{"name":"CEP","locatedIn":{"name":"ITAJUBÁ"}}
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

		if (!companyController.companyAlreadyExist(company)) { //create node for company because it is not exist in graph
			companyController.createCompany(company);
		}
			
		//Now we need to edit the user data on database
		Person person = new Person();
		person.setEmail(tokenDecoded.getEmail()); //set original e-mail from user
		person.setWorksIn(company);
		
		JSONObject jsonLivesIn = null;
		JSONObject jsonState = null;
		City cityLives = new City();
		UF ufLives = new UF();
		
		jsonData = new JSONObject(data);
		String livesIn = jsonData.getString("livesIn"); //{"name":"Itajubá","state":{"name":"Minas Gerais"}},"worksIn":{"name":"CEP1","locatedIn":{"name":"Itajubá","state":{"name":"Minas Gerais"}}},"district":"Centro","address":"Rua Nova, 100","token":"cGVkcm9AZ21haWwuY29tfHBlZHJv"}
		jsonLivesIn = new JSONObject(livesIn);
		jsonState = new JSONObject(jsonLivesIn.getString("state")); //{"name":"Minas Gerais"}
		
		cityLives.setName(jsonLivesIn.getString("name"));
		ufLives.setName(jsonState.getString("name"));
		cityLives.setUf(ufLives);
			
		person.setLivesIn(cityLives);
		person.setAddress(jsonData.getString("address"));
		person.setDistrict(jsonData.getString("district"));
			
		
		JSONArray objCreated = personController.createAccountWorkData(person);
		byte[] tokenUpdated = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());

		json = JSONUtil.generateJSONSuccessByData(true, "Sucesso ao editar a nova conta!", objCreated);
		json.put("token", new String(tokenUpdated));
		return json;
	}

	
	
	
	@GET
	@Path("/persondata/{token}")
	public JSONObject personData(@PathParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	
}