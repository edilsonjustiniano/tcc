package br.edu.univas.si.tcc.trunp.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil {

	public static JSONObject generateJSONSuccess(boolean success, String mesage) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("success", success);
		json.put("mesage", mesage);
			
		return json;
	}
	
	
	public static JSONObject generateJSONSuccessByData(boolean success, String mesage, String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		json.put("success", success);
		json.put("mesage", mesage);
			
		return json;
	}
	
	
	public static JSONObject generateJSONSuccessByData(boolean success, String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		json.put("success", success);
			
		return json;
	}
	
	
	public static JSONObject generateJSONSuccessByData(boolean success, JSONArray data) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("results", data);
		json.put("success", success);
			
		return json;
	}
	
	
	public static JSONObject generateJSONSuccessByData(boolean success, String mesage, JSONArray data) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("results", data);
		json.put("success", success);
		json.put("mesage", mesage);
			
		return json;
	}
	
	public static JSONObject generateJSONError(boolean success, String mesage) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("success", success);
		json.put("mesage", mesage);
			
		return json;
	}
	
	public static JSONObject generateJSONErrorSessionExpired(boolean success, String mesage, boolean expired) throws JSONException {
		JSONObject json = generateJSONError(success, mesage);
		json.put("sessionExpired", expired);
			
		return json;
	}
	
	public static List<JSONObject> parseJSONArrayToListJSON(JSONArray array) throws JSONException {
		List<JSONObject> response = new ArrayList<JSONObject>();
		
		for (int i = 0; i < array.length(); i++) {
			JSONArray arr1 = array.getJSONArray(i);
			for (int j = 0; j < arr1.length(); j++) {
				JSONObject obj = arr1.getJSONObject(j);
				System.out.println(obj);
				response.add(obj);
			}
			System.out.println(arr1);
		}
		
		return response;
	}
	
}
