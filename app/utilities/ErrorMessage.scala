package utilities

case class ErrorMessage(message: String, errorType: String)

object ErrorType{
  val Bad_Request = "bad_request"
  val Database_Error = "database_error"
  val Not_Found = "not_found"
  
}