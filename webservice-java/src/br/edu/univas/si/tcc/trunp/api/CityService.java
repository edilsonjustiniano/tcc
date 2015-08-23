package br.edu.univas.si.tcc.trunp.api;

import javax.ws.rs.PathParam;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.exception.JSONTrunpException;

public interface CityService {
	
	public JSONObject cities(@PathParam("state") String state) throws JSONTrunpException;

}
