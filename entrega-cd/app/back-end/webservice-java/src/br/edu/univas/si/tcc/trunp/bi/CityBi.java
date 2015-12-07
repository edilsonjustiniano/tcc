package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.CityDAO;

public class CityBi {

	private CityDAO dao;
	
	public CityBi() {
		this.dao = new CityDAO();
	}

	public JSONArray getAllCitiesByState(String uf) throws JSONException {
		return dao.getAllCitiesByState(uf);
	}
}
