package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.google.inject.Singleton

import javax.inject.Inject
import models.Customer
import models.Invoice
import models.Payment
import play.api.libs.json.JsError
import play.api.libs.json.JsObject
import play.api.mvc.Action
import play.api.mvc.BodyParsers
import play.api.mvc.Controller
import services.CustomerService
import services.InvoiceService
import services.PaymentService
import utilities.ErrorMessage
import utilities.JsonResponseGenerator

/**
 *
 * Created by Anisha Sampath Kumar
 */
class ApplicationController @Inject() (customerService: CustomerService, invoiceService: InvoiceService, paymentService: PaymentService) extends Controller {

  def index = Action {
    Ok("Welcome to REST API application")
  }

  def createCustomer = Action.async(BodyParsers.parse.json) { request =>
    val customer = request.body.validate[List[Customer]]
    customer.fold(
      errors => Future(JsonResponseGenerator.generateErrorResponse(JsError.toJson(errors))),
      customer => {
        customerService.addCustomers(customer).map(result =>
          result match {
            case Right(result: String)     => JsonResponseGenerator.generateResponse(result)
            case Left(error: ErrorMessage) => JsonResponseGenerator.generateErrorResponse(error)
          })
      })
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
