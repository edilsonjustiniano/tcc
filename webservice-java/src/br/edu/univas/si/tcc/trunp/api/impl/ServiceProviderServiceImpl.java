package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.ServiceProviderService;
import br.edu.univas.si.tcc.trunp.controller.ServiceController;
import br.edu.univas.si.tcc.trunp.controller.ServiceProviderController;
import br.edu.univas.si.tcc.trunp.controller.TokenController;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.JSONUtil;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

@Path("/serviceprovider")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceProviderServiceImpl implements ServiceProviderService {

	private TokenController tokenController = new TokenController();
	private ServiceProviderController serviceProviderController = new ServiceProviderController();
	
	@GET
	@Path("/byservice/{service}")
	public JSONObject serviceProvidersByService(@PathParam("service") String service,
												@QueryParam("token") String token) throws JSONException {
		
		System.out.println(service + token);
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", true);
		}

		JSONArray result = serviceProviderController.getServiceProvidersByService(person, service);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;		

	}

	
	@GET
	@Path("/ratingInMyNetworkPartners")
	public JSONObject ratingInMyNetworkPartners(@QueryParam("provider") String provider,
												@QueryParam("service") String service,
												@QueryParam("token") String token) throws JSONException {

		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}

		JSONArray result = serviceProviderController.getServiceProviderRatingInMyNetworkPartners(person, service, provider);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;		
	}

	
	@GET
	@Path("/ratingInMyCompany")
	public JSONObject ratingInMyCompany(@QueryParam("provider") String provider,
										@QueryParam("service") String service,
										@QueryParam("token") String token) throws JSONException {
		
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}

		JSONArray result = serviceProviderController.getServiceProviderRatingInMyCompany(person, service, provider);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;		

	}
	
	
	@GET
	@Path("/ratingInMyCity")
	public JSONObject ratingInMyCity(@QueryParam("provider") String provider,
									 @QueryParam("service") String service,
									 @QueryParam("token") String token) throws JSONException {

		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}
		
		JSONArray result = serviceProviderController.getServiceProviderRatingInMyCity(person, service, provider);
		json = JSONUtil.generateJSONSuccessByData(true, result);

		tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;
	}

	@GET
	@Path("/data")
	public JSONObject data(@QueryParam("provider") String provider,
						   @QueryParam("service") String service,
						   @QueryParam("token") String token) throws JSONException {
		
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String providerEmail = provider;
		String providerService = service;
		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", true);
		}

		JSONArray result = serviceProviderController.getServiceProviderData(providerEmail, providerService);
		json = JSONUtil.generateJSONSuccessByData(true, result);
		tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		
		return json;
	}

	@GET
	@Path("/myservices/{token}")
	public JSONObject myServices(@PathParam("token") String token) throws JSONException {
		
		System.out.println(token);
		JSONObject json = null;
		Token tokenDecoded = null;
		Person person = new Person();

		byte[] tokenByte = token.getBytes();
		
		tokenDecoded = Base64Util.decodeToken(tokenByte);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		/* Check if token is expired */
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}
		
	    JSONArray result = serviceProviderController.getMyServices(person);
	    json = JSONUtil.generateJSONSuccessByData(true, result);
	    tokenByte = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(tokenByte));
		
		return json;
	}
	
	@POST
	@Path("/addservice")
	public JSONObject addService(String data) throws JSONException {
		
		System.out.println(data);
		JSONObject json = null;
		JSONObject jsonData = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		byte[] token = null;
		
		jsonData = new JSONObject(data);
		token = jsonData.getString("token").getBytes();
		service = jsonData.getString("service");
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		/* Check if token is expired */
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}
		
		//Verificando se é um novo serviço
		boolean isNewService = serviceProviderController.isNewService(service);
		if (!isNewService) {
			boolean isAlreadyStored = serviceProviderController.isAlreadyStored(service, person);
		    if (isAlreadyStored) {
		    	json = JSONUtil.generateJSONError(true, "Serviço anteriormente registrado à você!");
				json.put("isAlreadyStored", true);
				token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
				json.put("token", new String(token));

				return json;
		    } else {
		    	
				JSONArray result = serviceProviderController.addService(service, person);
				json = JSONUtil.generateJSONSuccess(true, "Sucesso ao adicionar serviço!");
				json.put("isAlreadyStored", false);
				token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
				json.put("token", new String(token));
				
				return json;
		    }
		}
		
		ServiceController serviceController = new ServiceController();
		
	    serviceController.createService(service);
	    JSONArray result = serviceProviderController.addService(service, person);
	    json = JSONUtil.generateJSONSuccess(true, "Sucesso ao adicionar serviço!");
	    json.put("isAlreadyStored", false);
		token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(token));
		
		return json;
	}
	
	@POST
	@Path("/removeservice")
	public JSONObject removeService(String data) throws JSONException {
		
		System.out.println(data);
		JSONObject json = null;
		JSONObject jsonData = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		byte[] token = null;
		
		jsonData = new JSONObject(data);
		token = jsonData.getString("token").getBytes();
		service = jsonData.getString("service");
		
		tokenDecoded = Base64Util.decodeToken(token);
		person.setEmail(tokenDecoded.getEmail());
		person.setPassword(MD5Util.generateMD5(tokenDecoded.getPassword()));
		
		if (!tokenController.isValidSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão inválida!", false);
		}
		
		/* Check if token is expired */
		if (tokenController.isExpiredSession(tokenDecoded)) {
			return JSONUtil.generateJSONErrorSessionExpired(false, "Sessão expirada, por favor realize o login novamente!", false);
		}
		
	    JSONArray result = serviceProviderController.removeService(service, person);
		json = JSONUtil.generateJSONSuccess(true, "Sucesso ao remover serviço!");
		token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
		json.put("token", new String(token));
		
		return json;
	}

}
