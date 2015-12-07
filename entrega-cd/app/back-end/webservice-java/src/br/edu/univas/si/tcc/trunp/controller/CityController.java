package br.edu.univas.si.tcc.trunp.controller;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.bi.CityBi;

public class CityController {

	private CityBi cityBi;
	
	public CityController() {
		this.cityBi = new CityBi();
	}
	
	public JSONArray getAllCitiesByState(String uf) throws JSONException {
		return cityBi.getAllCitiesByState(uf);
	}
}
