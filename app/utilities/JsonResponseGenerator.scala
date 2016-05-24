package utilities

import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc.Result

/**
 *
 * Created by Anisha Sampath Kumar
 */

object JsonResponseGenerator extends ResponseGenerator {

  def generateResponse(data: String): Result = {
    var json = Json.obj("status" -> "OK", "message" -> data)
    Ok(json)
  }

   def generateResponse(data: JsObject): Result = {
    var json = Json.obj("status" -> "OK", "message" -> data)
    Ok(json)
  }
   
  def generateErrorResponse(e: JsObject): Result = {
    var json = Json.obj("status" -> "Not OK", "message" -> e)
    BadRequest(json)
  }
  
  def generateErrorResponse(error: ErrorMessage): Result = {
    var json = Json.obj("status" -> "Not OK", "error" -> error.message)
    error.errorType match {
      case ErrorType.Bad_Request    => BadRequest(json)
      case ErrorType.Database_Error => InternalServerError(json)
      case ErrorType.Not_Found      => NotFound(json)
    }
  }

} 