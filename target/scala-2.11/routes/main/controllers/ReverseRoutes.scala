
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/anisha/Downloads/REST-API/conf/routes
// @DATE:Mon May 23 17:36:45 EEST 2016

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseApplication(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def getCustomer(customerId:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "customer/" + implicitly[PathBindable[String]].unbind("customerId", dynamicString(customerId)))
    }
  
    // @LINE:10
    def createPayment(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "payment")
    }
  
    // @LINE:8
    def createInvoice(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "invoice")
    }
  
    // @LINE:6
    def createCustomer(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "customer")
    }
  
  }


}
