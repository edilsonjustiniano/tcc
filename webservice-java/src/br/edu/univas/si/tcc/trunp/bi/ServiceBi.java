package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.ServiceDAO;

public class ServiceBi {

	private ServiceDAO dao;
	
	public ServiceBi() {
		dao = new ServiceDAO();
	}

	public JSONArray getServiceByName(String service) throws JSONException {
		return dao.getServiceByName(service);
	}
}
