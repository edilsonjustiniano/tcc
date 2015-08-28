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

	public JSONArray getServiceProvidersByService(Person person, String service) throws JSONException {
		return serviceProviderBi.getServiceProvidersByService(person, service);
	}
	
	public JSONArray getServiceProviderData(String providerEmail, String providerService) throws JSONException {
		return serviceProviderBi.getServiceProviderData(providerEmail, providerService);
	}

	public JSONArray getServiceProviderRatingInMyNetworkPartners(Person person,
			String service, String provider) throws JSONException {
		return serviceProviderBi.getServiceProviderRatingInMyNetworkPartners(person, service, provider);
	}

	public JSONArray getServiceProviderRatingInMyCompany(Person person,
			String service, String provider) throws JSONException {
		return serviceProviderBi.getServiceProviderRatingInMyCompany(person, service, provider);
	}

	public JSONArray getServiceProviderRatingInMyCity(Person person,
			String service, String provider) throws JSONException {
		return serviceProviderBi.getServiceProviderRatingInMyCity(person, service, provider);
	}
}
