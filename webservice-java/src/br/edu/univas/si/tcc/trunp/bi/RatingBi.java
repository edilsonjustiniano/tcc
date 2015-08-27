package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.RatingDAO;
import br.edu.univas.si.tcc.trunp.model.Person;

public class RatingBi {

	private RatingDAO dao;
	
	public RatingBi() {
		this.dao = new RatingDAO();
	}

	public JSONArray getMyLastestRatings(Person person) throws JSONException {
		return dao.getMyLastestRatings(person);
	}
}
