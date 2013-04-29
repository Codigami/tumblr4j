package com.tumblr4j.tumblr;

import com.tumblr4j.tumblr.exception.TumblrException;


public class Dummy {
	
	public static void main(String[] args) throws TumblrException {
		
		/*Client client = new Client("enter-data-dont-checkin", "enter-data-dont-checkin");
		TumblrFactory facebookFactory = new TumblrFactory(client);
		
		Tumblr facebook = facebookFactory.getInstance(new OAuthAccessToken("enter-data-dont-checkin"));
	*/
		/*FqlPost[] fqlPosts = facebook.newsFeed();

		User fbUser = facebook.getCurrentUser();
		System.out.println("User id: "+fbUser.getId());
		
		for(FqlPost post : fqlPosts){
			
			System.out.println(post.getMessage()+" id: "+post.getPostId()+" "+post.getActorId());
			
		}*/
		
		/*Client client = new Client();
		TumblrFactory tumblrFactory = new TumblrFactory(client);
		OAuthAccessToken accessToken = new OAuthAccessToken();
				
		
		Tumblr tumblr = tumblrFactory.getInstance(accessToken);
   	Map<String, Object> userMap = tumblr.getUser("13456408");
		System.out.println(((Map<String, Object>)userMap.get("data")).get("id"));
		System.out.println(((Map<String, Object>)userMap.get("data")).get("username"));*/
		//accessToken();
		
	}

	private static void accessToken() throws TumblrException {
		/*String code = null;
		Client client = new Client("enter-data-dont-checkin", "enter-data-dont-checkin");
		TumblrFactory tumblrFactory = new TumblrFactory(client);
		
		String redirectURL = tumblrFactory.getRedirectURL("http://www.justunfollow.com/v2/tumblr/callback.html/", Permission.RELATIONSHIPS);
		
		System.out.println(redirectURL);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
	  try {
      code = br.readLine();
   } catch (IOException ioe) {
      System.out.println("IO error trying to read your name!");
      System.exit(1);
   }
	  
	  System.out.println(code);
	  
	  Map<String, Object> accessToken = tumblrFactory.getOAuthAccessToken(code, "http://www.justunfollow.com/v2/tumblr/callback.html/");
	  
	  System.out.println("access token is: "+((String)accessToken.get("access_token")));
	  
	  */
	}

}
