
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/anisha/Downloads/REST-API/conf/routes
// @DATE:Mon May 23 17:36:45 EEST 2016

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseApplication Application = new controllers.ReverseApplication(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseApplication Application = new controllers.javascript.ReverseApplication(RoutesPrefix.byNamePrefix());
  }

}
