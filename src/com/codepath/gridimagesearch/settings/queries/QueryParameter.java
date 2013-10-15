package com.codepath.gridimagesearch.settings.queries;

import java.io.Serializable;

import org.apache.http.NameValuePair;

public class QueryParameter implements NameValuePair, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7258554739107678197L;
	/**
	 * 
	 */
	public String key;
	public String value;	

	public QueryParameter(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String toString() {
		return this.key + "=" + this.value;
	}
	
	@Override
	public String getName() {
		return this.key;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
