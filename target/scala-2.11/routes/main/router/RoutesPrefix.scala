
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/anisha/Downloads/REST-API/conf/routes
// @DATE:Mon May 23 17:36:45 EEST 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
