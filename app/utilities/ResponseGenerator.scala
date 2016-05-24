package utilities

import play.api._
import play.api.mvc._

trait ResponseGenerator {
  
  def generateResponse(data: String): Result
  
  def generateErrorResponse(data: ErrorMessage): Result
  
}