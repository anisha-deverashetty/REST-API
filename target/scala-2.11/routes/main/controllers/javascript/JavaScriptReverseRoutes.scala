
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/anisha/Downloads/REST-API/conf/routes
// @DATE:Mon May 23 17:36:45 EEST 2016

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:6
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def getCustomer: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getCustomer",
      """
        function(customerId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "customer/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("customerId", encodeURIComponent(customerId0))})
        }
      """
    )
  
    // @LINE:10
    def createPayment: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.createPayment",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "payment"})
        }
      """
    )
  
    // @LINE:8
    def createInvoice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.createInvoice",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "invoice"})
        }
      """
    )
  
    // @LINE:6
    def createCustomer: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.createCustomer",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "customer"})
        }
      """
    )
  
  }


}
