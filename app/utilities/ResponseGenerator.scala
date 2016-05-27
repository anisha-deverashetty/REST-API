package utilities

import play.api.mvc.Result

/**
 * Created by Anisha Sampath Kumar
 */

/**
 * Holds methods to generate response
 */
trait ResponseGenerator {

  def generateResponse(data: String): Result

  def generateErrorResponse(data: ErrorMessage): Result
}
