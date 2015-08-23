package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.FeedService;

@Path("/feed")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeedServiceImpl implements FeedService {

	@GET
	@Path("/lastestpartnership")
	public JSONObject lastestPartnership(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/lastestratings")
	public JSONObject lastestRatings(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
