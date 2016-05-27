package utilities

/**
 * Created by Anisha Sampath Kumar
 */

//ErrorMessage entity 
case class ErrorMessage(message: String, errorType: String)

//holds error types
object ErrorType {
  val Bad_Request = "bad_request"
  val Database_Error = "database_error"
  val Not_Found = "not_found"
}