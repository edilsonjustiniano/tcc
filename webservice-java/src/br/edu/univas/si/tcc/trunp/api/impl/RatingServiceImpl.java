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
	
	@POST
	@Path("/save")
	public JSONObject rating(String data) {
		return null;
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
