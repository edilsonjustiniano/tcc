package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.controller.TokenController;
import br.edu.univas.si.tcc.dao.SessionDAO;
import br.edu.univas.si.tcc.dao.TokenDAO;
import br.edu.univas.si.tcc.model.Person;
import br.edu.univas.si.tcc.model.Token;
import br.edu.univas.si.tcc.util.Base64Util;
import br.edu.univas.si.tcc.util.MD5Util;

@Path("/session")
public class SessionResource {

	private SessionDAO dao = new SessionDAO();
	
	@Path("/login")
	@POST
	@Produces("application/json")
	public String login(String json) {
		
		System.out.println(json);
		JSONObject jsonObj = null;
		JSONObject response = null;
		Person person = new Person();
		
		try {
			jsonObj = new JSONObject(json);
			person.setEmail(jsonObj.getString("email").trim());
			person.setPassword(MD5Util.generateMD5(jsonObj.getString("password").trim()));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if (!dao.thereIsPersonOnDatabase(person)) {
			response = new JSONObject();
			try {
				response.put("success", false);
				response.put("mesage", "Usuário e/ou senha inválido(s)!");
				
				return response.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		byte[] token = null;

		try {
			response = new JSONObject();
			response.put("success", true);
			response.put("mesage", "Sucesso ao realizar login!");
			token = Base64Util.encodeToken(person.getEmail(), jsonObj.getString("password").trim());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		return response.toString();
	}
	
	
	@Path("/getUserInfo/")
	@POST
	@Produces("application/json")
	public String getUserInfoFromSession(String json) {
		
		System.out.println(json);
		JSONObject response = null;
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
		
		/* Check if session is valid */
		TokenDAO tokenDao = new TokenDAO();
		
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
		
		/* Check if token is expired */
		TokenController controller = new TokenController();
		if (controller.isExpiredSession(tokenDecoded)) {
			try {
				response = new JSONObject();
				response.put("success", false);
				response.put("mesage", "Sessão expirada, por favor realize o login novamente!");
				response.put("sessionExpired", true);
				return response.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(tokenDecoded.getPassword());
		/* Perform the query to get info about the user in session */
		String result = dao.getPersonDataInSession(person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Sucesso ao realizar login!");
			token = Base64Util.encodeToken(person.getEmail(), person.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		return response.toString();

	}

}
