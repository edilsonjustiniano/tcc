package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.UFBi;

public class UFController {

	private UFBi ufBi;
	
	public UFController() {
		this.ufBi = new UFBi();
	}

	public JSONArray getUfs() throws JSONException {
		return ufBi.getUfs();
	}
}
