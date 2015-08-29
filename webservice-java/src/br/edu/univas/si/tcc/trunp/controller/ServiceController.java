package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.ServiceBi;

public class ServiceController {

	private ServiceBi serviceBi;
	
	public ServiceController() {
		this.serviceBi = new ServiceBi();
	}
	
	public JSONArray getServiceByName(String service) throws JSONException {
		return serviceBi.getServiceByName(service);
	}

	public void createService(String service) {
		serviceBi.createService(service);
	} 
}
