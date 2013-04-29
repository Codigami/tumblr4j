package com.tumblr4j.tumblr.exception;

import java.io.Serializable;

public class GenericError implements Serializable {

	private static final long serialVersionUID = 1498422925103709414L;
	
	private String type;
	private String message;

	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

}
