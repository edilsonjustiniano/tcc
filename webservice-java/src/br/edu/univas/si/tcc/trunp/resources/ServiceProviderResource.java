package br.edu.univas.si.tcc.trunp.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.controller.TokenController;
import br.edu.univas.si.tcc.trunp.dao.ServiceProviderDAO;
import br.edu.univas.si.tcc.trunp.dao.TokenDAO;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.model.Token;
import br.edu.univas.si.tcc.trunp.util.Base64Util;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

@Path("/serviceProvider")
public class ServiceProviderResource {

	private ServiceProviderDAO dao = new ServiceProviderDAO();

	@Path("/setServices")
	@POST
	@Produces("application/json")
	public String setServices() {

		String responseService = dao.setServiceProvider(null);

		JSONObject response = null;
		try {
			response = new JSONObject(responseService);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			response.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	@Path("/getServiceProvidersByService")
	@POST
	@Produces("application/json")
	public String getServiceProvidersByService(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			service = jsonObj.getString("service");
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

		String result = dao.getServiceProvidersByService(person, service);
		
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
	
	
	
	
	
	@Path("/getServiceProviderRatingInMyNetworkPartners")
	@POST
	@Produces("application/json")
	public String getServiceProviderRatingInMyNetworkPartners(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		String serviceProvider = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			service = jsonObj.getString("service");
			serviceProvider = jsonObj.getString("serviceProvider");
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

		String result = dao.getServiceProviderRatingInMyNetworkPartners(person, service, serviceProvider);
		
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

	
	
	
	@Path("/getServiceProviderRatingInMyCompany")
	@POST
	@Produces("application/json")
	public String getServiceProviderRatingInMyCompany(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		String serviceProvider = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			service = jsonObj.getString("service");
			serviceProvider = jsonObj.getString("serviceProvider");
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

		String result = dao.getServiceProviderRatingInMyCompany(person, service, serviceProvider);
		
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
	
	
	
	
	@Path("/getServiceProviderRatingInMyCity")
	@POST
	@Produces("application/json")
	public String getServiceProviderRatingInMyCity(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		String serviceProvider = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			service = jsonObj.getString("service");
			serviceProvider = jsonObj.getString("serviceProvider");
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

		String result = dao.getServiceProviderRatingInMyCity(person, service, serviceProvider);
		
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

	
	
	
	
	@Path("/getServiceProviderData")
	@POST
	@Produces("application/json")
	public String getServiceProviderData(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String providerEmail = null;
		String providerService = null;
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			providerEmail = jsonObj.getString("provider");
			providerService = jsonObj.getString("service");
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

		String result = dao.getServiceProviderData(providerEmail, providerService);
		
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
	
	
	
	@Path("/saveEvaluate")
	@POST
	@Produces("application/json")
	public String saveEvaluate(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String providerEmail = null;
		String providerService = null;
		String comments = null;
		int note = 0;
		
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			providerEmail = jsonObj.getString("provider");
			providerService = jsonObj.getString("service");
			note = jsonObj.getInt("note");
			comments = jsonObj.getString("comments");
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
		
		/*
		 * First we need to check if there is an other evaluate from the same guy to the same service provider
		 * in the same service stored on database before. If so, the user cannot store another evaluate
		 */
		boolean isNewEvaluate = dao.isNewEvaluate(providerEmail, providerService, person);
		if (!isNewEvaluate) {
			try {
				response = new JSONObject();
				response.put("success", true);
				response.put("isNewEvaluate", false);
				response.put("mesage", "Avaliação realizada anteriormente!");
				token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
				response.put("token", new String(token));
				
				return response.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		String result = dao.saveEvaluate(providerEmail, providerService, note, comments, person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("isNewEvaluate", true);
			response.put("mesage", "Sucesso ao cadastrar avaliação!");
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}


	@Path("/getMyServices")
	@POST
	@Produces("application/json")
	public String getMyServices(String json) {
		
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
		
	    String result = dao.getMyServices(person);
		
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

	
	@Path("/addService")
	@POST
	@Produces("application/json")
	public String addService(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			service = jsonObj.getString("service");
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
		
		/*
		 * First we need to check if there is an other evaluate from the same guy to the same service provider
		 * in the same service stored on database before. If so, the user cannot store another evaluate
		 */
		
		//Verificando se é um novo serviço
		boolean isNewService = dao.isNewService(service);
		if (!isNewService) {
			boolean isAlreadyStored = dao.isAlreadyStored(service, person);
		    if (isAlreadyStored) {
		    	try {
		    		response = new JSONObject();
					response.put("success", true);
					response.put("mesage", "Serviço anteriormente registrado à você!");
					response.put("isAlreadyStored", true);
					token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
					response.put("token", new String(token));
					return response.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
		    	
		    } else {
		    	
				String result = dao.addService(service, person);
				try {
					response = new JSONObject(result);
					response.put("success", true);
					response.put("mesage", "Sucesso ao adicionar serviço!");
					token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
					response.put("token", new String(token));
					return response.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
		    }
		}
		
	    dao.createService(service);
	    String result = dao.addService(service, person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Sucesso ao adicionar serviço!");
			response.put("isAlreadyStored", false);
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}


	
	
	@Path("/removeService")
	@POST
	@Produces("application/json")
	public String removeService(String json) {
		
		System.out.println(json);
		JSONObject response = null;
		JSONObject jsonObj = null;
		Token tokenDecoded = null;
		Person person = new Person();
		String service = null;
		
		
		byte[] token = null;
		
		try {
			jsonObj = new JSONObject(json);
			token = jsonObj.getString("token").getBytes();
			service = jsonObj.getString("service");
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
		
	    String result = dao.removeService(service, person);
		
		try {
			response = new JSONObject(result);
			response.put("success", true);
			response.put("mesage", "Sucesso ao remover serviço!");
			token = Base64Util.encodeToken(tokenDecoded.getEmail(), tokenDecoded.getPassword());
			response.put("token", new String(token));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return response.toString();
	}

}
