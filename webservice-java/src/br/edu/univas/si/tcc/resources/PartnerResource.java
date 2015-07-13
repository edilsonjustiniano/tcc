package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.dao.PartnerDAO;
import br.edu.univas.si.tcc.model.Person;
import br.edu.univas.si.tcc.model.Token;
import br.edu.univas.si.tcc.util.Base64Util;
import br.edu.univas.si.tcc.util.MD5Util;

@Path("/partner")
public class PartnerResource {
	
	private PartnerDAO dao = new PartnerDAO();
	
	@Path("getPossiblePartners")
	@POST
	@Produces("application/json")
	public String getPossiblePartners(String json) {
		
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
		
		/*
		 * First we need define how it will be defined the order of precedency
		 * e.g. First the person that works with you, in the same company,
		 * second person whose lives in the same city of you, but ordered according 
		 * to the quantity of the common friends between you and her.
		 * Third person whose its work is in the same city of you work according
		 * to the quantity of the common partners between you and her.
		 * fourth person who contracted a person whose it was contracted by you too
		 */
		String result = dao.getPossiblePartners(person);
		
		
		return null;
	}

}
