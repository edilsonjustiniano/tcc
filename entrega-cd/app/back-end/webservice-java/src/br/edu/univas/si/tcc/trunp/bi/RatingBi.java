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

	public boolean isNewEvaluate(String providerEmail, String providerService,
			Person person) {
		return dao.isNewEvaluate(providerEmail, providerService, person);
	}

	public JSONArray saveRating(String providerEmail, String providerService,
			int note, String comments, Person person) throws JSONException {
		return dao.saveRating(providerEmail, providerService, note, comments, person);
	}
}
