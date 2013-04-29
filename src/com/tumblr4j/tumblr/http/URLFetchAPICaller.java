package com.tumblr4j.tumblr.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.tumblr4j.tumblr.TumblrProp;
import com.tumblr4j.tumblr.exception.TumblrException;
import com.tumblr4j.tumblr.util.JSONToObjectTransformer;

public class URLFetchAPICaller implements APICallerInterface {

	public Map<String, Object> getData(String url, NameValuePair[] nameValuePairs) throws TumblrException {

		URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
		URL fetchURL = null;

		HTTPResponse response = null;
		String responseString = null;
		String constructedParams = null;



			if (nameValuePairs != null) {
				constructedParams = constructParams(nameValuePairs);

				if (url.contains("?")) {
					url = url.concat("&" + constructedParams);
				} else {
					url = url.concat("?" + constructedParams);
				}
			}

      int retry = Integer.parseInt(TumblrProp.get("NETWORK_FAILURE_RETRY_COUNT"));
      while (retry > 0){
        try {
          fetchURL = new URL(url);
          response = fetchService.fetch(fetchURL);
          break;
        } catch (IOException ex) {
          retry --;
          if(retry <= 0){
            throw new TumblrException("IO Exception while calling tumblr!", ex);
          }
        }
      }

			int statusCode = response.getResponseCode();
			if (statusCode != HttpStatus.SC_OK) {
				// TumblrError error = new TumblrError(statusCode,
				// "I guess you are not permitted to access this url. HTTP status code:"+statusCode, null);
				responseString = new String(response.getContent());
				throw new TumblrException(JSONToObjectTransformer.getError(responseString, statusCode));
			}
			responseString = new String(response.getContent());


		// if response string contains accessToken=xxx remove it!
		// responseString = Util.replaceAccessToken(responseString, nameValuePairs);

			ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> responseMap = null;
	    try {
				responseMap = mapper.readValue(responseString, Map.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}    
	    
    return responseMap;
	}

	/**
	 * @param url
	 * @param nameValuePairs
	 * @return
	 * @throws TumblrException
	 */
	public String postData(String url, NameValuePair[] nameValuePairs) throws TumblrException {

		String content = null;
		String constructedParams = null;
		int statusCode = 0;
		HttpURLConnection connection = null;
    int retry = Integer.parseInt(TumblrProp.get("NETWORK_FAILURE_RETRY_COUNT"));
    while (retry > 0){
      try {
        URL posturl = new URL(url);
        connection = (HttpURLConnection) posturl.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        // connection.setConnectTimeout(10000);
        // connection.setReadTimeout(10000);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

        constructedParams = constructParams(nameValuePairs);

        writer.write(constructedParams);
        writer.close();

        statusCode = connection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
          // "I guess you are not permitted to access this url. HTTP status code:"+statusCode, null);
          content = getResponse(connection);
          throw new TumblrException(JSONToObjectTransformer.getError(content, statusCode));
        } else {
          content = getResponse(connection);
        }
        break;
      } catch (MalformedURLException e) {
        throw new TumblrException("Malformed URL Exception while calling Tumblr!", e);
      } catch (IOException e) {
          retry --;
          if(retry <= 0){
            throw new TumblrException("IO Exception while calling facebook!", e);
          }
          if (connection != null) {
            connection.disconnect();
            connection = null;
          }
      } finally {
        if (connection != null) {
          connection.disconnect();
        }
      }
    }

    return content;

	}

	private String getResponse(HttpURLConnection connection) throws IOException {
		String content;
		// Get Response
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		content = response.toString();
		return content;
	}

	/*
	 * public String deleteData(String url, NameValuePair[] nameValuePairs) throws TumblrException {
	 * 
	 * String content = null; String constructedParams = null; int statusCode = 0; HttpURLConnection
	 * connection = null; try {
	 * 
	 * constructedParams = constructParams(nameValuePairs);
	 * 
	 * 
	 * 
	 * URL posturl = new URL(url+"/?"+constructedParams); connection = (HttpURLConnection)
	 * posturl.openConnection(); connection.setRequestProperty( "Content-Type",
	 * "application/x-www-form-urlencoded" ); connection.setDoOutput(true);
	 * connection.setRequestMethod("DELETE"); // connection.setConnectTimeout(10000); //
	 * connection.setReadTimeout(10000);
	 * 
	 * //connection.connect();
	 * 
	 * //System.out.println(connection.getContent());
	 * 
	 * OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	 * 
	 * writer.write(""); writer.close();
	 * 
	 * statusCode = connection.getResponseCode(); if (statusCode != HttpURLConnection.HTTP_OK) {
	 * content = getResponse(connection); throw new
	 * TumblrException(JSONToObjectTransformer.getError(content, statusCode));
	 * 
	 * } else { content = getResponse(connection);
	 * 
	 * } } catch (MalformedURLException e) { throw new
	 * TumblrException("Malformed URL Exception while calling Tumblr!", e); } catch (IOException
	 * e) { throw new TumblrException("IOException while calling Tumblr!", e); } finally { if
	 * (connection != null) { connection.disconnect(); } }
	 * 
	 * return content;
	 * 
	 * }
	 */

	public String deleteData(String url, NameValuePair[] nameValuePairs) throws TumblrException {
		String content = null;
		String constructedParams = null;
		int statusCode = 0;

		URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
		URL posturl = null;
		constructedParams = constructParams(nameValuePairs);

		try {
			posturl = new URL(url + "?" + constructedParams);
		} catch (MalformedURLException e) {
		}

		try {
			HTTPResponse response = fetchService.fetch(new HTTPRequest(posturl, HTTPMethod.DELETE));

			statusCode = response.getResponseCode();

			if (statusCode != HttpURLConnection.HTTP_OK) {
				content = new String(response.getContent());
				throw new TumblrException(JSONToObjectTransformer.getError(content, statusCode));
			} else {
				content = new String(response.getContent());
			}

		} catch (IOException e) {
		}

		return content;
	}

	private String constructParams(NameValuePair[] nameValuePairs) {

		StringBuilder builder = null;
		String constructedParams = null;

		for (NameValuePair nameValuePair : nameValuePairs) {
			if (nameValuePair != null && nameValuePair.getName() != null && nameValuePair.getValue() != null) {
				if (builder != null) {
					try {
						builder.append("&" + nameValuePair.getName() + "=" + URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO: Catch error
					}
				} else {
					builder = new StringBuilder();
					try {
						builder.append(nameValuePair.getName() + "=" + URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO: Catch error
					}
				}
			}
		}

		if (builder != null) {
			constructedParams = builder.toString();
		}

		return constructedParams;
	}

}