package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.ServiceProviderBi;
import br.edu.univas.si.tcc.trunp.model.Person;

public class ServiceProviderController {

	private ServiceProviderBi serviceProviderBi;
	
	public ServiceProviderController() {
		this.serviceProviderBi = new ServiceProviderBi();
	}

	public JSONArray getMyServices(Person person) throws JSONException {
		return serviceProviderBi.getMyServices(person);
	}
}
