package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.PartnerService;

@Path("/partner")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PartnerServiceImpl implements PartnerService {

	@GET
	@Path("/possiblepartners")
	public JSONObject possiblePartners(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GET
	@Path("/possiblepartners")
	public JSONObject allPartners(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@POST
	@Path("/possiblepartners")
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
	@Path("/allpartnerrequest")
	public JSONObject allPartnerRequest(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/acceptpartnerrequest")
	public JSONObject acceptPartnerRequest(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/rejectpartnerrequest")
	public JSONObject rejectPartnerRequest(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/searchnewpartners")
	public JSONObject searchNewPartners(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/searchnewpartners/onlybyname")
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
