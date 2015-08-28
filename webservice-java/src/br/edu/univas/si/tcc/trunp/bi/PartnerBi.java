package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.PartnerDAO;
import br.edu.univas.si.tcc.trunp.model.Person;

public class PartnerBi {
	
	private PartnerDAO dao;
	
	public PartnerBi() {
		dao = new PartnerDAO();
	}

	public JSONArray getPossiblePartners(Person person) throws JSONException {
		return dao.getPossiblePartners(person);
	}

	public JSONArray getAllRequestPartner(Person person) throws JSONException {
		return dao.getAllRequestPartner(person);
	}

	public JSONArray acceptPartnerRequest(Person person, String partnerEmail) throws JSONException {
		return dao.acceptPartnerRequest(person, partnerEmail);
	}

	public JSONArray rejectPartnerRequest(Person person, String partnerEmail) throws JSONException {
		return dao.rejectPartnerRequest(person, partnerEmail);
	}

	public JSONArray getAllPartners(Person person) throws JSONException {
		return dao.getAllPartners(person);
	}

	public boolean isMyPartner(Person person, String partner) {
		return dao.isMyPartner(person, partner);
	}

	public JSONArray getCommonsPartners(Person person, String partner) throws JSONException {
		return dao.getCommonsPartners(person, partner);
	}

}
