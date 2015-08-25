package br.edu.univas.si.tcc.trunp.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.dao.RatingDAO;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

@Path("/rating")
public class RatingResource {

	private RatingDAO dao = new RatingDAO();
	
	@Path("/myLastestRatings")
	@POST
	@Produces("application/json")
	public String myLastestRatings(String json) {
		
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
		
		String result = dao.getMyLastestRatings(person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}
	
}