
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/anisha/Downloads/REST-API/conf/routes
// @DATE:Mon May 23 17:36:45 EEST 2016

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  Application_0: controllers.Application,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    Application_0: controllers.Application
  ) = this(errorHandler, Application_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Application_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """customer""", """controllers.Application.createCustomer"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """invoice""", """controllers.Application.createInvoice"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """payment""", """controllers.Application.createPayment"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """customer/""" + "$" + """customerId<[^/]+>""", """controllers.Application.getCustomer(customerId:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_Application_createCustomer0_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("customer")))
  )
  private[this] lazy val controllers_Application_createCustomer0_invoker = createInvoker(
    Application_0.createCustomer,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "createCustomer",
      Nil,
      "POST",
      """ Home page""",
      this.prefix + """customer"""
    )
  )

  // @LINE:8
  private[this] lazy val controllers_Application_createInvoice1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("invoice")))
  )
  private[this] lazy val controllers_Application_createInvoice1_invoker = createInvoker(
    Application_0.createInvoice,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "createInvoice",
      Nil,
      "POST",
      """""",
      this.prefix + """invoice"""
    )
  )

  // @LINE:10
  private[this] lazy val controllers_Application_createPayment2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("payment")))
  )
  private[this] lazy val controllers_Application_createPayment2_invoker = createInvoker(
    Application_0.createPayment,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "createPayment",
      Nil,
      "POST",
      """""",
      this.prefix + """payment"""
    )
  )

  // @LINE:12
  private[this] lazy val controllers_Application_getCustomer3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("customer/"), DynamicPart("customerId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getCustomer3_invoker = createInvoker(
    Application_0.getCustomer(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getCustomer",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """customer/""" + "$" + """customerId<[^/]+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_Application_createCustomer0_route(params) =>
      call { 
        controllers_Application_createCustomer0_invoker.call(Application_0.createCustomer)
      }
  
    // @LINE:8
    case controllers_Application_createInvoice1_route(params) =>
      call { 
        controllers_Application_createInvoice1_invoker.call(Application_0.createInvoice)
      }
  
    // @LINE:10
    case controllers_Application_createPayment2_route(params) =>
      call { 
        controllers_Application_createPayment2_invoker.call(Application_0.createPayment)
      }
  
    // @LINE:12
    case controllers_Application_getCustomer3_route(params) =>
      call(params.fromPath[String]("customerId", None)) { (customerId) =>
        controllers_Application_getCustomer3_invoker.call(Application_0.getCustomer(customerId))
      }
  }
}
