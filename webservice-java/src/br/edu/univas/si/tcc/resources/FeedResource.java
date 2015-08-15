package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.FeedDAO;
import br.edu.univas.si.tcc.dao.RatingDAO;
import br.edu.univas.si.tcc.model.Person;
import br.edu.univas.si.tcc.model.Token;
import br.edu.univas.si.tcc.util.Base64Util;
import br.edu.univas.si.tcc.util.MD5Util;

@Path("/feed")
public class FeedResource {

	private FeedDAO dao = new FeedDAO();
	
	@Path("/lastPartnership")
	@POST
	@Produces("application/json")
	public String lastPartnership(String json) {
		
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
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		String result = dao.getLastPartnership(person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

	
	@Path("/lastestRatings")
	@POST
	@Produces("application/json")
	public String lastestRatings(String json) {
		
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
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		String result = dao.getLastestRatings(person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

}
