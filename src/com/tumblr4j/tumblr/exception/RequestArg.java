package com.tumblr4j.tumblr.exception;

import java.io.Serializable;

public class RequestArg implements Serializable {

	private static final long serialVersionUID = -1592199468365884633L;
	
	public String key;
	public String value;

	
	private RequestArg() {
		super();
	}


	public RequestArg(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

}
