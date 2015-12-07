package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.FeedDAO;
import br.edu.univas.si.tcc.trunp.model.Person;

public class FeedBi {

	private FeedDAO dao;
	
	public FeedBi() {
		dao = new FeedDAO();
	}
	
	public JSONArray getLastestPartnership(Person person) throws JSONException {
		return dao.getLastestPartnership(person);
	}

	public JSONArray getLastestRatings(Person person) throws JSONException {
		return dao.getLastestRatings(person);
	}

}
