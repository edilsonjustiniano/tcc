package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.RatingService;

@Path("/rating")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingServiceImpl implements RatingService {

	@POST
	@Path("/save")
	public JSONObject rating(String data) {
		return null;
	}

	@GET
	@Path("/mylastestratings")
	public JSONObject myLastestRatings(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
