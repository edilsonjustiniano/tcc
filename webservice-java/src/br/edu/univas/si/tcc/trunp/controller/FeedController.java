package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.FeedBi;
import br.edu.univas.si.tcc.trunp.model.Person;

public class FeedController {

	private FeedBi feedBi;
	
	public FeedController() {
		this.feedBi = new FeedBi();
	}
	
	public JSONArray getLastestPartnership(Person person) throws JSONException {
		return feedBi.getLastestPartnership(person);
	}
	
	public JSONArray getLastestRatings(Person person) throws JSONException {
		return feedBi.getLastestRatings(person);
	}
}
