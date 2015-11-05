package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.bi.PersonBi;
import br.edu.univas.si.tcc.trunp.model.Person;

public class PersonController {
	
	private PersonBi personBi;
	
	public PersonController() {
		this.personBi = new PersonBi();
	}
	
	public boolean isFreeEmail(String email) {
		return personBi.isFreeEmail(email);
	}

	public JSONArray createAccountPersonalData(JSONObject jsonData) throws JSONException {
		return personBi.createAccountPersonalData(jsonData);
	}
	
	public JSONArray createAccountWorkData(Person person) throws JSONException {
		return personBi.createAccountWorkData(person);
	}

	public JSONArray getPersonData(String partner) throws JSONException {
		return personBi.getPersonData(partner);
	}
	
	public JSONArray edit(Person person) throws JSONException {
		return personBi.edit(person);
	}
}
