package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.ServiceProviderDAO;
import br.edu.univas.si.tcc.trunp.model.Person;

public class ServiceProviderBi {

	private ServiceProviderDAO dao;
	
	public ServiceProviderBi() {
		dao = new ServiceProviderDAO();
	}

	public JSONArray getMyServices(Person person) throws JSONException {
		return dao.getMyServices(person);
	}

	public JSONArray getServiceProvidersByService(Person person, String service) throws JSONException {
		return dao.getServiceProvidersByService(person, service);
	}

	public JSONArray getServiceProviderData(String providerEmail,
			String providerService) throws JSONException {
		return dao.getServiceProviderData(providerEmail, providerService);
	}

	public JSONArray getServiceProviderRatingInMyNetworkPartners(Person person,
			String service, String provider) throws JSONException {
		return dao.getServiceProviderRatingInMyNetworkPartners(person, service, provider);
	}

	public JSONArray getServiceProviderRatingInMyCompany(Person person,
			String service, String provider) throws JSONException {
		return dao.getServiceProviderRatingInMyCity(person, service, provider);
	}

	public JSONArray getServiceProviderRatingInMyCity(Person person,
			String service, String provider) throws JSONException {
		return dao.getServiceProviderRatingInMyCompany(person, service, provider);
	}
}
