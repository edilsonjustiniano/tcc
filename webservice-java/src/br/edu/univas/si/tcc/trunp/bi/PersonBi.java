package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.dao.PersonDAO;
import br.edu.univas.si.tcc.trunp.model.Person;

public class PersonBi {

	private PersonDAO dao;
	
	public PersonBi() {
		dao = new PersonDAO();
	}
	
	public boolean isFreeEmail(String email) {
		return dao.isFreeEmail(email);
	}

	 public JSONArray createAccountPersonalData(JSONObject jsonData) throws JSONException {
		return dao.createAccountPersonalData(jsonData);
	}

	public JSONArray createAccountWorkData(Person person) throws JSONException {
		return dao.createAccountWorkData(person);
	}

	public JSONArray getPersonData(String partner) throws JSONException {
		return dao.getPersonData(partner);
	}
	
	

}
