package br.edu.univas.si.tcc.trunp.model;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class UF {

	private String name;
	private String abbreviation;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	@Override
	public String toString() {
		return "UF [name=" + name + ", abbreviation=" + abbreviation + "]";
	}
	public long getSize(UF arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
			MediaType arg4) {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	public void writeTo(UF arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
			MediaType arg4, MultivaluedMap<String, Object> arg5,
			OutputStream arg6) throws IOException, WebApplicationException {
		// TODO Auto-generated method stub
		
	}
	
	
}
