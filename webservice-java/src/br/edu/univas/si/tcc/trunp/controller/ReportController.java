package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.ReportBi;

public class ReportController {
	private ReportBi reportBi;

	public ReportController() {
		this.reportBi = new ReportBi();
	}

	public JSONArray lastEvaluateOfServiceProvider(String user,
			String serviceProvider, String service, int limit)
			throws JSONException {

		return reportBi.lastEvaluateOfServiceProvider(user, serviceProvider,
				service, limit);

	}

	public JSONArray lastEvaluateOfServiceInNetwork(String token,
			String serviceProvider, String service, int limit) throws JSONException {


		return reportBi.lastEvaluateOfServiceInNetwork(token, serviceProvider,
				service, limit);
	}

}
