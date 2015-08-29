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

import br.edu.univas.si.tcc.trunp.api.RatingService;
import br.edu.univas.si.tcc.trunp.controller.RatingController;
import br.edu.univas.si.tcc.trunp.controller.TokenController;
import br.edu.univas.si.tcc.trunp.dao.TokenDAO;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

@Path("/rating")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingServiceImpl implements RatingService {

	private RatingController ratingController = new RatingController();
	private TokenController tokenController = new TokenController();
	
	@POST
	@Path("/save")
	public JSONObject save(String data) throws JSONException {

		System.out.println(data);
		JSONObject json = null;
		JSONObject jsonData = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String providerEmail = null;
		String providerService = null;
		String comments = null;
		int note = 0;
		byte[] token = null;
		
		jsonData = new JSONObject(data);
		token = jsonData.getString("token").getBytes();
		providerEmail = jsonData.getString("provider");
		providerService = jsonData.getString("service");
		note = jsonData.getInt("note");
		comments = jsonData.getString("comments");
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));

		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}
		
		/*
		 * First we need to check if there is an other evaluate from the same guy to the same service provider
		 * in the same service stored on database before. If so, the user cannot store another evaluate
		 */
		boolean isNewEvaluate = ratingController.isNewEvaluate(providerEmail, providerService, person);
		if (!isNewEvaluate) {
			
			json = JSONUtil.generateJSONError(true, "Avaliação realizada anteriormente!");
			json.put("isNewEvaluate", false);
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			json.put("token", new String(token));
				
			return json;
		}
		
		JSONArray result = ratingController.saveRating(providerEmail, providerService, note, comments, person);
		json = JSONUtil.generateJSONSuccessByData(true, "Sucesso ao cadastrar avaliação!", result);
		json.put("isNewEvaluate", true);
		token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(token));
		
		return json;
	}

	@GET
	@Path("/mylastestratings/{token}")
	public JSONObject myLastestRatings(@PathParam("token")String token) throws JSONException {

		System.out.println(token);
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		JSONArray result = ratingController.getMyLastestRatings(person);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		
		return json;
	}

	
}
