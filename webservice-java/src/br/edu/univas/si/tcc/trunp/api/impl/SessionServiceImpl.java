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

import br.edu.univas.si.tcc.trunp.api.SessionService;
import br.edu.univas.si.tcc.trunp.controller.SessionController;
import br.edu.univas.si.tcc.trunp.controller.TokenController;
import br.edu.univas.si.tcc.trunp.dao.TokenDAO;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;

@Path("/session")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionServiceImpl implements SessionService {
	
	private SessionController sessionController = new SessionController();
	private TokenController tokenController = new TokenController();
	
	@POST
	@Path("/login")
	public JSONObject login(String data) throws JSONException {
		
		System.out.println(data);
		JSONObject json = new JSONObject();
		JSONObject jsonData = null;
		jsonData = new JSONObject(data);
		
		Person person = sessionController.generatePerson(data);
		
		if (!sessionController.thereIsPersonOnDatabase(person)) {
			try {
				json.put("success", false);
				json.put("mesage", "Usuário e/ou senha inválido(s)!");
				return json;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		byte[] token = null;

		try {
			json.put("success", true);
			json.put("mesage", "Sucesso ao realizar login!");
			token = Base64Util.encodeToken(person.getEmail(), jsonData.getString("password").trim());
			json.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		return json;
	}

	@GET
	@Path("/userinfo/{token}")
	public JSONObject userInfo(@PathParam("token") String token) throws JSONException {
		
		System.out.println(token);
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		
		byte[] tokenByte = token.getBytes();
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida", false);
		}
		
		/* Check if token is expired */
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", true);
		}

		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(tokenDecoded.getPassword());
		
		
		/* Perform the query to get info about the user in session */
		JSONArray result = sessionController.getPersonDataInSession(person);
		json = JSONUtil.generateJSONSuccessByData(true, "Sucesso ao realizar login!", result);
		tokenByte = Base64Util.encodeToken(person.getEmail(), person.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;
	}

}
