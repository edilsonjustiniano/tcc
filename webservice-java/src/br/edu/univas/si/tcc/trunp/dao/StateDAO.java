package br.edu.univas.si.tcc.trunp.dao;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.util.JSONUtil;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class StateDAO {

	public JSONArray getAllStates() throws JSONException {

		WebResource resource = FactoryDAO.GetInstance();

		String query = "MATCH (uf:UF) RETURN {name: uf.name, abbreviation: uf.abbreviation} as UF;";

		String request = "{\"query\":\"" + query + "\"}";
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(request)
				.post(ClientResponse.class);
		System.out.println(response);
		String resp = response.getEntity(String.class);
		
		JSONObject json = null;
		JSONArray objData = null;
		json = new JSONObject(resp);
		objData = json.getJSONArray("data");
		List<JSONObject> parser = JSONUtil.parseJSONArrayToListJSON(objData);
		System.out.println(parser);

		JSONArray arr = new JSONArray(parser);
		System.out.println(arr);

		return arr;
	}

}
