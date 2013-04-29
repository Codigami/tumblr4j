package com.tumblr4j.tumblr.exception;

public class TumblrException extends Exception {

	private static final long serialVersionUID = 529406704223138896L;
	
	private TumblrError error;

  public TumblrException(String msg, Exception exception) {
      super(msg, exception);
  }

  public TumblrException(TumblrError error) {
		super();
		this.error = error;
	}

	public TumblrError getError() {
		return error;
	}

	public void setError(TumblrError error) {
		this.error = error;
	}
	
	@Override
	public String getMessage() {
		return (this.getError() == null) ? super.getMessage() : this.getError().getErrorMsg();
	}
	
	@Override
	public String getLocalizedMessage() {
		return this.getMessage();
	}

}