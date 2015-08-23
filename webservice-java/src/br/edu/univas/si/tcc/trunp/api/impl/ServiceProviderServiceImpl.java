package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.ServiceProviderService;

@Path("/serviceprovider")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceProviderServiceImpl implements ServiceProviderService {

	@GET
	@Path("/byservice/{service}")
	public JSONObject serviceProvidersByService(@PathParam("service") String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	
	@GET
	@Path("/ratingInMyNetworkPartners")
	public JSONObject ratingInMyNetworkPartners(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	
	@GET
	@Path("/ratingInMyCompany")
	public JSONObject ratingInMyCompany(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	@GET
	@Path("/ratingInMyCity")
	public JSONObject ratingInMyCity(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/data")
	public JSONObject data(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/myservices/{token}")
	public JSONObject myServices(@QueryParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@POST
	@Path("/addservice")
	public JSONObject addService(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@DELETE
	@Path("/removeservice")
	public JSONObject removeService(String data) {
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

}
