package com.tumblr4j.tumblr;

import java.io.IOException;
import java.util.Properties;

public class TumblrProp {
	

  private static Properties prop;

  public static String get(String key){
    if(prop == null){
      prop = new Properties();
      try {
        prop.load(TumblrProp.class.getClassLoader().getResourceAsStream("tumblr4j.properties"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return prop.getProperty(key);
  }
}
