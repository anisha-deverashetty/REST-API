package controllers

import play.api.libs.json._
import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import models._
import services._
import utilities._
/**
 *
 * Created by Anisha Sampath Kumar
 */
class Application @Inject() (customerService: CustomerService, invoiceService: InvoiceService, paymentService: PaymentService) extends Controller {

  def createCustomer = Action.async(BodyParsers.parse.json) { request =>

    val customer = request.body.validate[List[Customer]]
    customer.fold(
      errors => Future(JsonResponseGenerator.generateErrorResponse(JsError.toJson(errors))),
      customer =>
        customerService.addCustomers(customer).map(result =>
          result match {
            case Right(result: String)     => JsonResponseGenerator.generateResponse(result)
            case Left(error: ErrorMessage) => JsonResponseGenerator.generateErrorResponse(error)
          }))
  }

  def createInvoice = Action.async(BodyParsers.parse.json) { request =>

    val invoice = request.body.validate[List[Invoice]]
    invoice.fold(
      errors => Future(JsonResponseGenerator.generateErrorResponse(JsError.toJson(errors))),
      invoice =>
        invoiceService.addInvoices(invoice).map(result =>
          result match {
            case Right(result: String)     => JsonResponseGenerator.generateResponse(result)
            case Left(error: ErrorMessage) => JsonResponseGenerator.generateErrorResponse(error)
          }))
  }
  
  def createPayment = Action.async(BodyParsers.parse.json) { request =>

    val payment = request.body.validate[List[Payment]]
    payment.fold(
      errors => Future(JsonResponseGenerator.generateErrorResponse(JsError.toJson(errors))),
      payment =>
        paymentService.addPayments(payment).map(result =>
          result match {
            case Right(result: String)     => JsonResponseGenerator.generateResponse(result)
            case Left(error: ErrorMessage) => JsonResponseGenerator.generateErrorResponse(error)
          }))
  }

  def getCustomer(customerId: String) = Action.async {

    val res = customerService.getAllCustomerData(customerId)
    res.map(result =>
      result match {
        case Right(result: JsObject)   => JsonResponseGenerator.generateResponse(result)
        case Left(error: ErrorMessage) => JsonResponseGenerator.generateErrorResponse(error)
      })
  }

}
