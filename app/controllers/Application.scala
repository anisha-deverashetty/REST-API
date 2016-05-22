package controllers

import play.api.libs.json._
import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import models._
import services._

/**
 *
 * Created by Anisha Sampath Kumar
 */
class Application @Inject() (customerService: CustomerService, invoiceService: InvoiceService, paymentService: PaymentService) extends Controller {

  def createCustomer = Action.async(BodyParsers.parse.json) { request =>

    val customer = request.body.validate[List[Customer]]
    customer.fold(
      errors => Future(BadRequest(Json.obj(
        "status" -> "Not OK",
        "error" -> JsError.toJson(errors)))),
      customer =>
        customerService.addCustomers(customer).map(result => {
          result match {
            case Right(result: String) => Ok(Json.obj("status" -> "ok", "message" -> result))
            case Left(result: Exception) => BadRequest(Json.obj("status" -> "Not OK",
              "error" -> result.getCause.getMessage))
          }
        }))
  }

  def createInvoice = Action.async(BodyParsers.parse.json) { request =>

    val invoice = request.body.validate[List[Invoice]]
    invoice.fold(
      errors => Future(BadRequest(Json.obj(
        "status" -> "Not OK",
        "error" -> JsError.toJson(errors)))),
      invoice =>
        invoiceService.addInvoices(invoice).map(result => {
          result match {
            case Right(result: String) => Ok(Json.obj("status" -> "ok", "message" -> result))
            case Left(result: Exception) => BadRequest(Json.obj("status" -> "Not OK",
              "error" -> result.getCause.getMessage))
          }
        }))
  }

  def createPayment = Action.async(BodyParsers.parse.json) { request =>

    val payment = request.body.validate[List[Payment]]
    payment.fold(
      errors => Future(BadRequest(Json.obj(
        "status" -> "Not OK",
        "error" -> JsError.toJson(errors)))),
      payment =>
        paymentService.addPayments(payment).map(result => {
          result match {
            case Right(result: String) => Ok(Json.obj("status" -> "ok", "message" -> result))
            case Left(result: Exception) => BadRequest(Json.obj("status" -> "Not OK",
              "error" -> result.getCause.getMessage))
          }
        }))
  }
}
