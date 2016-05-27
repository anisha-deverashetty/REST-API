package test

import scala.Left
import scala.Right
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import com.google.inject.Singleton

import controllers.ApplicationController
import models.Customer
import models.Invoice
import models.Payment
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.Result
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsJson
import play.api.test.Helpers.defaultAwaitTimeout
import play.api.test.Helpers.status
import services.CustomerService
import services.InvoiceService
import services.PaymentService
import utilities.ErrorMessage
import utilities.ErrorType

/**
 * Created by Anisha Sampath Kumar
 */

/**
 * Unit test for AppilcationController
 */
@RunWith(classOf[JUnitRunner])
class ApplicationControllerSpec extends Specification with Results with Mockito {

  val mockCustomerService = mock[CustomerService]
  val mockInvoiceService = mock[InvoiceService]
  val mockPaymentService = mock[PaymentService]
  val testController = new ApplicationController(mockCustomerService, mockInvoiceService, mockPaymentService)

  val fakeCustomerId = "c_01"
  val fakeCustomerData1 = List(Json.obj(
    "customer_id" -> "c_01",
    "customer_firstname" -> "Steve",
    "customer_lastname" -> "John",
    "customer_email" -> "steve@gmail.com",
    "customer_phone" -> "3584999999",
    "customer_address" -> "kamppi",
    "customer_zipcode" -> "00100",
    "customer_city" -> "Helsinki",
    "customer_country" -> "Finland"))

  val fakeCustomerData2 =
    Json.obj("customer_id" -> "c_02", "customer_firstname" -> "John")

  val fakeCustomerErrorData = List(Json.obj(
    "customer_id" -> "c_01",
    "customer_lastname" -> "John",
    "customer_email" -> "steve@gmail.com",
    "customer_phone" -> "3584999999"))

  val fakeInvoiceData = List(Json.obj(
    "invoice_id" -> "in_1001",
    "customer_id" -> "Cus_01",
    "net_amount" -> 10,
    "vat_amount" -> 2.9,
    "total_amount" -> 12.9,
    "invoice_datetime" -> "2016-05-22 23:09:33",
    "warehouse_id" -> "w_01",
    "supplier_id" -> "S_01",
    "salesperson_id" -> "sp_01"))

  val fakeInvoiceErrorData = List(Json.obj(
    "customer_id" -> "Cus_01",
    "total_amount" -> 12.9,
    "invoice_datetime" -> "2016-05-22 23:09:33"))

  val fakePaymentData = List(Json.obj(
    "payment_id" -> "1",
    "invoice_id" -> "in10001",
    "payment_datetime" -> "2016-05-22 23:09:33",
    "payment_type" -> "Visa Card"))

  val fakePaymentErrorData = List(Json.obj(
    "payment_id" -> "1",
    "invoice_id" -> "in10001",
    "payment_type" -> "Visa Card"))

  "Customers#create" should {
    "create customer(s)" in {
      mockCustomerService.addCustomers(any[List[Customer]]) returns Future(Right("1 Customer(s) added"))
      val request = FakeRequest().withBody(Json.toJson(fakeCustomerData1))
      val result: Future[Result] = testController.createCustomer().apply(request)
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").as[String] must be equalTo "1 Customer(s) added"
      there was one(mockCustomerService).addCustomers(any[List[Customer]])
    }
  }

  "Customers#create" should {
    "return internal server error for database exception" in {
      val errorMessage = new ErrorMessage("SQL Exception", ErrorType.Database_Error)
      mockCustomerService.addCustomers(any[List[Customer]]) returns Future(Left(errorMessage))
      val request = FakeRequest().withBody(Json.toJson(fakeCustomerData1))
      val result: Future[Result] = testController.createCustomer().apply(request)
      status(result) must be equalTo 500
    }
  }

  "Customers#create" should {
    "check for mandatory fields" in {
      val request = FakeRequest().withBody(Json.toJson(fakeCustomerErrorData))
      val result: Future[Result] = testController.createCustomer().apply(request)
      status(result) must be equalTo 400
    }
  }

  "Customers#get" should {
    "get all customer data with invoice and payment details" in {
      mockCustomerService.getAllCustomerData(any[String]) returns Future(Right(fakeCustomerData2))
      val result: Future[Result] = testController.getCustomer(fakeCustomerId).apply(FakeRequest())
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").get must be equalTo fakeCustomerData2
      there was one(mockCustomerService).getAllCustomerData(any[String])
    }
  }

  "Customers#get" should {
    "return not found when id is not available" in {
      val errorMessage = new ErrorMessage("Customer id not found", ErrorType.Not_Found)
      mockCustomerService.getAllCustomerData(any[String]) returns Future(Left(errorMessage))
      val result: Future[Result] = testController.getCustomer(fakeCustomerId).apply(FakeRequest())
      status(result) must be equalTo 404
      (contentAsJson(result) \ "message").as[String] must be equalTo "Customer id not found"
    }
  }

  "Invoices#create" should {
    "create invoice(s)" in {
      mockInvoiceService.addInvoices(any[List[Invoice]]) returns Future(Right("1 Invoice(s) added"))
      val request = FakeRequest().withBody(Json.toJson(fakeInvoiceData))
      val result: Future[Result] = testController.createInvoice().apply(request)
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").as[String] must be equalTo "1 Invoice(s) added"
      there was one(mockInvoiceService).addInvoices(any[List[Invoice]])
    }
  }

  "Invoices#create" should {
    "check for mandatory fields" in {
      val request = FakeRequest().withBody(Json.toJson(fakeInvoiceErrorData))
      val result: Future[Result] = testController.createInvoice().apply(request)
      status(result) must be equalTo 400
    }
  }

  "Invoices#create" should {
    "return internal server error for database exception" in {
      val errorMessage = new ErrorMessage("SQL Exception", ErrorType.Database_Error)
      mockInvoiceService.addInvoices(any[List[Invoice]]) returns Future(Left(errorMessage))
      val request = FakeRequest().withBody(Json.toJson(fakeInvoiceData))
      val result: Future[Result] = testController.createInvoice().apply(request)
      status(result) must be equalTo 500
    }
  }

  "Payments#create" should {
    "create payment(s)" in {
      mockPaymentService.addPayments(any[List[Payment]]) returns Future(Right("1 Payment(s) added"))
      val request = FakeRequest().withBody(Json.toJson(fakePaymentData))
      val result: Future[Result] = testController.createPayment().apply(request)
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").as[String] must be equalTo "1 Payment(s) added"
      there was one(mockPaymentService).addPayments(any[List[Payment]])
    }
  }

  "Payments#create" should {
    "check for mandatory fields" in {
      val request = FakeRequest().withBody(Json.toJson(fakePaymentErrorData))
      val result: Future[Result] = testController.createPayment().apply(request)
      status(result) must be equalTo 400
    }
  }

  "Payments#create" should {
    "return internal server error for database exception" in {
      val errorMessage = new ErrorMessage("SQL Exception", ErrorType.Database_Error)
      mockPaymentService.addPayments(any[List[Payment]]) returns Future(Left(errorMessage))
      val request = FakeRequest().withBody(Json.toJson(fakePaymentData))
      val result: Future[Result] = testController.createPayment().apply(request)
      status(result) must be equalTo 500
    }
  }
}