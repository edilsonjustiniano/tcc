package br.edu.univas.si.tcc.trunp.controller;

import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import br.edu.univas.si.tcc.trunp.bi.ReportBi;

public class ReportController {
	private ReportBi reportBi;

	public ReportController() {
		this.reportBi = new ReportBi();
	}
	
	public JSONArray lastEvaluateOfServiceProvider(String user, String serviceProvider, 
			 String service, int limit){
		
		return reportBi.lastEvaluateOfServiceProvider(user, serviceProvider, service, limit);
		
		
	}
	

}
