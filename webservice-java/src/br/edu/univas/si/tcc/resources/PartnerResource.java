package br.edu.univas.si.tcc.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.controller.TokenController;
import br.edu.univas.si.tcc.dao.PartnerDAO;
import br.edu.univas.si.tcc.dao.TokenDAO;
import br.edu.univas.si.tcc.model.Person;
import br.edu.univas.si.tcc.model.Token;
import br.edu.univas.si.tcc.util.Base64Util;
import br.edu.univas.si.tcc.util.MD5Util;

@Path("/partner")
public class PartnerResource {
	
	private PartnerDAO dao = new PartnerDAO();
	
	@Path("/getPossiblePartners")
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
		 * second person who lives in the same city of you, but ordered according 
		 * to the quantity of the common friends between you and her.
		 * Third person whose its work is in the same city of you work according
		 * to the quantity of the common partners between you and her.
		 * fourth person who contracted a person whose it was contracted by you too
		 */
		String result = dao.getPossiblePartners(person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

	@Path("/getAllPartners")
	@POST
	@Produces("application/json")
	public String getAllPartners(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		int limit = 0;
		int offset = 0;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			limit = jsonObj.getInt("limit");
			offset = jsonObj.getInt("offset");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		String result = dao.getAllPartners(limit, offset, person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}
	
	
	
	@Path("/addPartner")
	@POST
	@Produces("application/json")
	public String addPartner(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partnerEmail = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			partnerEmail = jsonObj.getString("partner");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
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

		String result = dao.addPartner(person, partnerEmail);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Requisição de parceria enviada com sucesso!");
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

	
	@Path("/getAllPartnerRequest")
	@POST
	@Produces("application/json")
	public String getAllPartnerRequest(String json) {
		
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

		String result = dao.getAllRequestPartner(person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Requisição de parceria enviada com sucesso!");
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}
	
	
	
	@Path("/acceptPartnerRequest")
	@POST
	@Produces("application/json")
	public String acceptPartnerRequest(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partnerEmail = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			partnerEmail = jsonObj.getString("partner");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
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

		String result = dao.acceptPartnerRequest(person, partnerEmail);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Requisição de parceria aceita!");
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}
	
	
	
	@Path("/rejectPartnerRequest")
	@POST
	@Produces("application/json")
	public String rejectPartnerRequest(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partnerEmail = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			partnerEmail = jsonObj.getString("partner");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
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

		String result = dao.rejectPartnerRequest(person, partnerEmail);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Requisição de parceria aceita!");
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}
	
	
	@Path("/searchNewPartners")
	@POST
	@Produces("application/json")
	public String searchNewPartners(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partner = null;
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			partner = jsonObj.getString("partner");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		/*
		 * First we need define how it will be defined the order of precedency
		 * e.g. First the person that works with you, in the same company,
		 * second person who lives in the same city of you, but ordered according 
		 * to the quantity of the common friends between you and her.
		 * Third person whose its work is in the same city of you work according
		 * to the quantity of the common partners between you and her.
		 * fourth person who contracted a person whose it was contracted by you too
		 */
		String result = dao.searchNewPartners(person, partner);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

	
	
	@Path("/searchNewPartnersOnlyByName")
	@POST
	@Produces("application/json")
	public String searchNewPartnersOnlyByName(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partner = null;
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			partner = jsonObj.getString("partner");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		/*
		 * First we need define how it will be defined the order of precedency
		 * e.g. First the person that works with you, in the same company,
		 * second person who lives in the same city of you, but ordered according 
		 * to the quantity of the common friends between you and her.
		 * Third person whose its work is in the same city of you work according
		 * to the quantity of the common partners between you and her.
		 * fourth person who contracted a person whose it was contracted by you too
		 */
		String result = dao.searchNewPartnersOnlyByName(person, partner);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

}
