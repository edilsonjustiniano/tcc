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

import br.edu.univas.si.tcc.trunp.api.PartnerService;
import br.edu.univas.si.tcc.trunp.controller.PartnerController;
import br.edu.univas.si.tcc.trunp.controller.TokenController;
import br.edu.univas.si.tcc.trunp.dao.TokenDAO;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

@Path("/partner")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PartnerServiceImpl implements PartnerService {

	private PartnerController partnerController = new PartnerController();
	private TokenController tokenController = new TokenController();
	
	@GET
	@Path("/possiblepartners/{token}")
	public JSONObject possiblePartners(@PathParam("token") String token) throws JSONException {
		
		System.out.println(token);
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
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
		JSONArray result = partnerController.getPossiblePartners(person);
		
		json = JSONUtil.generateJSONSuccessByData(true, result);
		return json;
	}
	
	@GET
	@Path("/allpartners")
	public JSONObject allPartners(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@POST
	@Path("/add")
	public JSONObject add(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/cancel")
	public JSONObject cancel(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/allpartnerrequest/{token}")
	public JSONObject allPartnerRequest(@PathParam("token") String token) throws JSONException {
		
		System.out.println(token);
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}

		JSONArray result = partnerController.getAllRequestPartner(person);//dao.getAllRequestPartner(person);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;
	}

	@POST
	@Path("/acceptpartnerrequest")
	public JSONObject acceptPartnerRequest(String data) throws JSONException {
		
		System.out.println(data);
		JSONObject json = null;
		JSONObject jsonData = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partnerEmail = null;
		
		byte[] token = null;
		
		jsonData = new JSONObject(data);
		token = jsonData.getString("token").getBytes();
		partnerEmail = jsonData.getString("partner");
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}

		JSONArray result = partnerController.acceptPartnerRequest(person, partnerEmail);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(token));
		
		return json;

	}

	@POST
	@Path("/rejectpartnerrequest")
	public JSONObject rejectPartnerRequest(String data) throws JSONException {

		System.out.println(data);
		JSONObject json = null;
		JSONObject jsonData = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String partnerEmail = null;
		
		byte[] token = null;
		
		jsonData = new JSONObject(data);
		token = jsonData.getString("token").getBytes();
		partnerEmail = jsonData.getString("partner");
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}
		
		JSONArray result = partnerController.rejectPartnerRequest(person, partnerEmail);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(token));
		
		return json;
	}

	@POST
	@Path("/searchnewpartners")
	public JSONObject searchNewPartners(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/searchnewpartnersonlybyname")
	public JSONObject searchNewPartnersOnlyByName(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/ismypartner")
	public JSONObject isMyPartner(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/commonspartners")
	public JSONObject commonsPartners(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}
