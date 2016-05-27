package utilities

import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.Result
import play.api.mvc.Results.BadRequest
import play.api.mvc.Results.InternalServerError
import play.api.mvc.Results.NotFound
import play.api.mvc.Results.Ok

/**
 * Created by Anisha Sampath Kumar
 */

/**
 * Generates json response
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
    var json = Json.obj("status" -> "Not OK", "message" -> error.message)
    error.errorType match {
      case ErrorType.Bad_Request    => BadRequest(json)
      case ErrorType.Database_Error => InternalServerError(json)
      case ErrorType.Not_Found      => NotFound(json)
    }
  }

} 