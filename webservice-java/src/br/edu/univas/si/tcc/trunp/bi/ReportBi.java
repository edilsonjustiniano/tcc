package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;

import br.edu.univas.si.tcc.trunp.dao.ReportDAO;

public class ReportBi {
	private ReportDAO dao;

	public ReportBi() {
		dao = new ReportDAO();
	}

	public JSONArray lastEvaluateOfServiceProvider(String user,
			String serviceProvider, String service, int limit) {
		
		return dao.lastEvaluateOfServiceProvider(user, serviceProvider, service, limit);
	}

}
