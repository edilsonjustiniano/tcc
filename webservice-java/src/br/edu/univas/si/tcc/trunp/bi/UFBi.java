package br.edu.univas.si.tcc.trunp.bi;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import br.edu.univas.si.tcc.trunp.dao.StateDAO;

public class UFBi {

	private StateDAO dao;
	
	public UFBi() {
		this.dao = new StateDAO();
	}

	public JSONArray getUfs() throws JSONException {
		return dao.getAllStates();
	}
}
