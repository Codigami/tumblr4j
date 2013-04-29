package com.tumblr4j.tumblr.exception;

import java.io.Serializable;

public class TumblrError implements Serializable {

	private static final long serialVersionUID = -8815087847493738979L;
	
	private int errorCode;
	private String errorMsg;
	private RequestArg[] requestArgs;

	/* Keeping a no args constructor*/
	private TumblrError() {
		super();
	}

	public TumblrError(int errorCode, String errorMsg, RequestArg[] requestArgs) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.requestArgs = requestArgs;
	}



	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public RequestArg[] getRequestArgs() {
		return requestArgs;
	}

}
