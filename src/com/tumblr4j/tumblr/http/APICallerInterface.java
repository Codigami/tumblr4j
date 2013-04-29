package com.tumblr4j.tumblr.http;


import java.util.Map;

import org.apache.http.NameValuePair;

import com.tumblr4j.tumblr.exception.TumblrException;

public interface APICallerInterface {
	
	public Map<String, Object> getData(String url, NameValuePair[] nameValuePairs) throws TumblrException;

  public String postData(String url, NameValuePair[] nameValuePairs) throws TumblrException;
  
  public String deleteData(String url, NameValuePair[] nameValuePairs) throws TumblrException;
}
