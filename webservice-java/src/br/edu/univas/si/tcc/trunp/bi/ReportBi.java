package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.ReportDAO;

public class ReportBi {
	private ReportDAO dao;

	public ReportBi() {
		dao = new ReportDAO();
	}

	public JSONArray lastEvaluateOfServiceProvider(String user,
			String serviceProvider, String service, int limit)
			throws JSONException {

		return dao.lastEvaluateOfServiceProvider(user, serviceProvider,
				service, limit);
	}

	public JSONArray lastEvaluateOfServiceInNetwork(String token,
			String serviceProvider, String service, int limit) throws JSONException {

		return dao.lastEvaluateOfServiceInNetwork(token, serviceProvider,
				service, limit);
	}
}
