package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.RatingBi;
import br.edu.univas.si.tcc.trunp.model.Person;

public class RatingController {

	private RatingBi ratingBi;
	
	public RatingController() {
		this.ratingBi = new RatingBi();
	}
	
	public JSONArray getMyLastestRatings(Person person) throws JSONException {
		return ratingBi.getMyLastestRatings(person);
	}

	public boolean isNewEvaluate(String providerEmail, String providerService,
			Person person) {
		return ratingBi.isNewEvaluate(providerEmail, providerService, person);
	}

	public JSONArray saveRating(String providerEmail, String providerService,
			int note, String comments, Person person) throws JSONException {
		return ratingBi.saveRating(providerEmail, providerService, note, comments, person);
	}
}
