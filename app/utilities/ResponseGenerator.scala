package utilities

import play.api.mvc.Result

trait ResponseGenerator {

  def generateResponse(data: String): Result

  def generateErrorResponse(data: ErrorMessage): Result
}
