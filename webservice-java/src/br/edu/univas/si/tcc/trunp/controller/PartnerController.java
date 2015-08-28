package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.PartnerBi;
import br.edu.univas.si.tcc.trunp.model.Person;

public class PartnerController {

	private PartnerBi partnerBi;
	
	public PartnerController() {
		this.partnerBi = new PartnerBi();
	}
	
	public JSONArray getPossiblePartners(Person person) throws JSONException {
		return partnerBi.getPossiblePartners(person);
	}

	public JSONArray getAllRequestPartner(Person person) throws JSONException {
		return partnerBi.getAllRequestPartner(person);
	}
	
	public JSONArray acceptPartnerRequest(Person person, String partnerEmail) throws JSONException {
		return partnerBi.acceptPartnerRequest(person, partnerEmail);
	}

	public JSONArray rejectPartnerRequest(Person person, String partnerEmail) throws JSONException {
		return partnerBi.rejectPartnerRequest(person, partnerEmail);
	}

	public JSONArray getAllPartners(Person person) throws JSONException {
		return partnerBi.getAllPartners(person);
	}

	public boolean isMyPartner(Person person, String partner) {
		return partnerBi.isMyPartner(person, partner);
	}

	public JSONArray getCommonsPartners(Person person, String partner) throws JSONException {
		return partnerBi.getCommonsPartners(person, partner);
	}
}
