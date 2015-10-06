package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.ReportService;
import br.edu.univas.si.tcc.trunp.controller.ReportController;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

@Path("/report")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReportServiceImpl implements ReportService {

	private ReportController reportController = new ReportController();

	@GET
	@Path("/lastEvaluateOfServiceProvider")
	public JSONObject lastEvaluateOfServiceProvider(
			@QueryParam("token") String token,
			@QueryParam("serviceProvider") String serviceProvider,
			@QueryParam("service") String service,
			@QueryParam("limit") int limit) throws JSONException {

		Token tokenDecoded = null;
		Person person = new Person();
		byte[] tokenByte = token.getBytes();

		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));

		JSONArray response = reportController.lastEvaluateOfServiceProvider(
				person.getEmail(), serviceProvider, service, limit);
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, response);

		return json;
	}

	@GET
	@Path("/lastEvaluateOfServiceInNetwork")
	public JSONObject lastEvaluateOfServiceInNetwork(
			@QueryParam("token") String token,
			@QueryParam("serviceProvider") String serviceProvider,
			@QueryParam("service") String service,
			@QueryParam("limit") int limit) throws JSONException {

		Token tokenDecoded = null;
		Person person = new Person();
		byte[] tokenByte = token.getBytes();

		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));

		JSONArray response = reportController.lastEvaluateOfServiceInNetwork(
				person.getEmail(), serviceProvider, service, limit);
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, response);

		return json;
	}

	@GET
	@Path("/lastEvaluate")
	public JSONObject lastEvaluate(	@QueryParam("token") String token, 
									@QueryParam("service") String service,
									@QueryParam("limit") int limit) throws JSONException {
		
		Token tokenDecoded = null;
		Person person = new Person();
		byte[] tokenByte = token.getBytes();

		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		
		JSONArray response = reportController.lastEvaluate(person.getEmail(), service, limit);
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, response);

		return json;
	}

	@GET
	@Path("/lastEvaluateInMyCity")
	public JSONObject lastEvaluateInMyCity( @QueryParam("token") String token, 
											@QueryParam("service") String service,
											@QueryParam("limit") int limit) throws JSONException {
		
		Token tokenDecoded = null;
		Person person = new Person();
		byte[] tokenByte = token.getBytes();

		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		
		JSONArray response = reportController.lastEvaluateInMyCity(person.getEmail(), service, limit);
		JSONObject json = JSONUtil.generateJSONSuccessByData(true, response);

		return json;
	}

}
