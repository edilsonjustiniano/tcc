package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.univas.si.tcc.trunp.dao.SessionDAO;
import br.edu.univas.si.tcc.trunp.model.Person;
import br.edu.univas.si.tcc.trunp.util.MD5Util;

public class SessionBi {
	
	private SessionDAO dao;

	public SessionBi() {
		this.dao = new SessionDAO();
	}

	public Person generatePerson(String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		Person person = new Person();
		person.setEmail(json.getString("email").trim());
		person.setPassword(MD5Util.generateMD5(json.getString("password").trim()));
		return person;
	}

	public boolean thereIsPersonOnDatabase(Person person) {
		return dao.thereIsPersonOnDatabase(person);
	}

	public JSONArray getPersonDataInSession(Person person) throws JSONException {
		return dao.getPersonDataInSession(person);
	}
	

}
