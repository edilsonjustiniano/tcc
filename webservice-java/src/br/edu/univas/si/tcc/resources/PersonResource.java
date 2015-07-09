package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.PersonDAO;
import br.edu.univas.si.tcc.model.Token;
import br.edu.univas.si.tcc.util.Base64Util;

import com.sun.jersey.core.util.Base64;

@Path("/person")
public class PersonResource {
	
	private PersonDAO dao = new PersonDAO();

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
		
		String objectCreate = dao.createAccountWorkData(jsonObj);
		
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
			System.out.println("decodedBytes " + tokenDecoded.getEmail() + Base64Util.BASE64_TOKEN_SEPARATOR + tokenDecoded.getPassword());
			
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
		try {
			jsonObj = new JSONObject(json);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		/* 1 -> Firstly, check if the session is valid, case the session is not valid, we do not need to do anything. Otherwise, continue.
		 * 2 -> Check if there is a company in the same city with the same nome, before to cadastre a new company.
		 * 3 -> And last, edit the user data on database
		 * Check if there is a valid session, informed by user and get the node
		 * regarding him
		 */
		
		return null;
	}
	
}
