package com.tumblr4j.tumblr.exception;

import java.io.Serializable;

public class HttpError implements Serializable{

	private static final long serialVersionUID = 5670946141370670413L;
	
	private GenericError error;

	public GenericError getError() {
		return error;
	}
	
}
