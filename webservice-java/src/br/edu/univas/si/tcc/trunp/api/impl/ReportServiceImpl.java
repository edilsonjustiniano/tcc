package br.edu.univas.si.tcc.trunp.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.api.ReportService;

@Path("/report")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReportServiceImpl implements ReportService {

	@Path("/lastEvaluateOfServiceProvider")
	public JSONObject lastEvaluateOfServiceProvider(@QueryParam("token")String token,
			@QueryParam("serviceProvider")String serviceProvider, @QueryParam("service") String service, 
			@QueryParam("limit")int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Path("/lastEvaluateOfServiceInNetwork")
	public JSONObject lastEvaluateOfServiceInNetwork(@QueryParam("token")String token,
			@QueryParam("serviceProvider")String serviceProvider, @QueryParam("service") String service, 
			@QueryParam("limit")int limit) {
		
		// TODO Auto-generated method stub
		
		return null;
	}

}
