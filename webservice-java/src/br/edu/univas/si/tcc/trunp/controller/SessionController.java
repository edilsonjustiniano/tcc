package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.SessionBi;
import br.edu.univas.si.tcc.trunp.model.Person;

public class SessionController {
	
	SessionBi sessionBi = new SessionBi();
	
	public Person generatePerson(String data) throws JSONException {
		return sessionBi.generatePerson(data);
	}

	public boolean thereIsPersonOnDatabase(Person person) {
		return sessionBi.thereIsPersonOnDatabase(person);
	}
	
	public JSONArray getPersonDataInSession(Person person) throws JSONException {
		return sessionBi.getPersonDataInSession(person);
		
	}

}
