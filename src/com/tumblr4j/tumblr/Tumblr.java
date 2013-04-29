package com.tumblr4j.tumblr;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tumblr4j.tumblr.enums.HttpClientType;
import com.tumblr4j.tumblr.enums.Relationship;
import com.tumblr4j.tumblr.exception.TumblrException;
import com.tumblr4j.tumblr.http.APICallerFactory;
import com.tumblr4j.tumblr.http.APICallerInterface;
import com.tumblr4j.tumblr.util.Constants;

/**
 * This is the main Tumblr class that will have methods which return Tumblr data as well as
 * publish data to Tumblr.
 * 
 * @author Nischal Shetty - nischalshetty85@gmail.com
 */
public class Tumblr implements Serializable {

	private static final long serialVersionUID = 4093692752523132886L;

	Logger logger = Logger.getLogger(Tumblr.class.getName());

	private OAuthAccessToken authAccessToken;

	private APICallerInterface caller = null;

	/**
	 * If only the access token is passed, then the Apache Http Client library is used for making http
	 * requests
	 * 
	 * @param authAccessToken
	 */
	public Tumblr(OAuthAccessToken authAccessToken) {
		// apache http client is the default client type
		this(authAccessToken, HttpClientType.APACHE_HTTP_CLIENT);
	}

	public Tumblr(OAuthAccessToken authAccessToken, HttpClientType clientType) {
		this.authAccessToken = authAccessToken;
		caller = APICallerFactory.getAPICallerInstance(clientType);
	}

	/**
	 * Returns a Tumblr user's available info.
	 * 
	 * @param fbId
	 * @return
	 * @throws TumblrException
	 */
	public Map<String, Object> getUser(String fbId) throws TumblrException {
		NameValuePair[] nameValuePairs = { new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken()) };
		return pullData(Constants.TUMBLR_GRAPH_URL + "/" + "users" + "/" + fbId+"/", nameValuePairs);
	}

  public String relationship(String fbId, Relationship relationship) throws TumblrException {
    NameValuePair[] nameValuePairs = new NameValuePair[1];
    nameValuePairs[0] = new BasicNameValuePair(Constants.PARAM_ACTION, relationship.toString().toLowerCase());
    return postData(Constants.TUMBLR_GRAPH_URL + "/" + "users" + "/" + fbId+"/relationship?access_token=" + this.authAccessToken.getAccessToken(), nameValuePairs);
  }

  public Map<String, Object>  relationship(String fbId) throws TumblrException {
    NameValuePair[] nameValuePairs =  new NameValuePair[1];
    nameValuePairs[0] = new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken());
    return pullData(Constants.TUMBLR_GRAPH_URL + "/" + "users" + "/" + fbId+"/relationship", nameValuePairs);
  }

  /**
   * Get the list of users this user follows.
   *
   * @param fbId
   * @return
   * @throws TumblrException
   */
  public Map<String, Object> follows(String fbId, String cursor) throws TumblrException {
    NameValuePair[] nameValuePairs = null;
    if(cursor == null){
      nameValuePairs = new NameValuePair[1];
      nameValuePairs[0] = new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken());
    }else{
      nameValuePairs = new NameValuePair[2];
      nameValuePairs[0] = new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken());
      nameValuePairs[1] = new BasicNameValuePair(Constants.PARAM_CURSOR, cursor);
    }
    return pullData(Constants.TUMBLR_GRAPH_URL + "/" + "users" + "/" + fbId+"/follows", nameValuePairs);
  }

  /**
   * Get the list of users this user is followed by.
   *
   * @param fbId
   * @return
   * @throws TumblrException
   */
  public Map<String, Object> followedBy(String fbId, String cursor) throws TumblrException {
    NameValuePair[] nameValuePairs = null;
    if(cursor == null){
      nameValuePairs = new NameValuePair[1];
      nameValuePairs[0] = new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken());
    }else{
      nameValuePairs = new NameValuePair[2];
      nameValuePairs[0] = new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken());
      nameValuePairs[1] = new BasicNameValuePair(Constants.PARAM_CURSOR, cursor);
    }
    return pullData(Constants.TUMBLR_GRAPH_URL + "/" + "users" + "/" + fbId+"/followed-by", nameValuePairs);
  }
	
	public OAuthAccessToken getAuthAccessToken() {
		return authAccessToken;
	}

	/**
	 * Raw API method to pull any data in json form and transform it into the right object <br>
	 * An HTTP GET method is used here
	 * 
	 * @param url
	 * @param nameValuePairs Pass parameters that need to accompany the call
	 * @return
	 * @throws TumblrException
	 */
	public Map<String, Object> pullData(String url, NameValuePair[] nameValuePairs) throws TumblrException {
		// APICaller would retrieve the json string object from Tumblr by making a https call
		// Once the json string object is obtaind, it is passed to obj transformer and the right object
		// is retrieved
    return caller.getData(url, nameValuePairs);
	}

  /**
   * Raw API method to post any data in json form and transform it into the right object <br>
   * An HTTP POST method is used here
   *
   * @param url
   * @param nameValuePairs Pass parameters that need to accompany the call
   * @return
   * @throws TumblrException
   */
  public String postData(String url, NameValuePair[] nameValuePairs) throws TumblrException {
    // APICaller would retrieve the json string object from Tumblr by making a https call
    // Once the json string object is obtaind, it is passed to obj transformer and the right object
    // is retrieved
    return caller.postData(url, nameValuePairs);
  }
	
}