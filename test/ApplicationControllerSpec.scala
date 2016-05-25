package test

import controllers._
import services._
import models._
import utilities._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.{ Result, _ }
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ ExecutionContext, Future }

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

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

  //Example : Required field is missing
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
      mockCustomerService.addCustomers(any[List[Customer]]) returns Future(Right("1 entities added"))
      val request = FakeRequest().withBody(Json.toJson(fakeCustomerData1))
      val result: Future[Result] = testController.createCustomer().apply(request)
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").as[String] must be equalTo "1 entities added"
      there was one(mockCustomerService).addCustomers(any[List[Customer]])
    }
  }

  "Customers#create" should {
    "If something goes wrong with Database" in {
      val errorMessage = new ErrorMessage("SQL Exception: Connection to database failure", "database_error")
      mockCustomerService.addCustomers(any[List[Customer]]) returns Future(Left(errorMessage))
      val request = FakeRequest().withBody(Json.toJson(fakeCustomerData1))
      val result: Future[Result] = testController.createCustomer().apply(request)
      status(result) must be equalTo 500
    }
  }

  "Customers#create" should {
    "requires all mandatory fields" in {
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
    "tries to retrive customer data for not existing id" in {
      val errorMessage = new ErrorMessage("Customer id not found", "not_found")
      mockCustomerService.getAllCustomerData(any[String]) returns Future(Left(errorMessage))
      val result: Future[Result] = testController.getCustomer(fakeCustomerId).apply(FakeRequest())
      status(result) must be equalTo 404
      (contentAsJson(result) \ "message").as[String] must be equalTo "Customer id not found"
    }
  }

  "Invoices#create" should {
    "create invoice(s)" in {
      mockInvoiceService.addInvoices(any[List[Invoice]]) returns Future(Right("1 entities added"))
      val request = FakeRequest().withBody(Json.toJson(fakeInvoiceData))
      val result: Future[Result] = testController.createInvoice().apply(request)
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").as[String] must be equalTo "1 entities added"
      there was one(mockInvoiceService).addInvoices(any[List[Invoice]])
    }
  }

  "Invoices#create" should {
    "requires all mandatory fields" in {
      val request = FakeRequest().withBody(Json.toJson(fakeInvoiceErrorData))
      val result: Future[Result] = testController.createInvoice().apply(request)
      status(result) must be equalTo 400
    }
  }

  "Invoices#create" should {
    "If something goes wrong with Database" in {
      val errorMessage = new ErrorMessage("SQL Exception: Connection to database failure", "database_error")
      mockInvoiceService.addInvoices(any[List[Invoice]]) returns Future(Left(errorMessage))
      val request = FakeRequest().withBody(Json.toJson(fakeInvoiceData))
      val result: Future[Result] = testController.createInvoice().apply(request)
      status(result) must be equalTo 500
    }
  }

  "Payments#create" should {
    "create payment" in {
      mockPaymentService.addPayments(any[List[Payment]]) returns Future(Right("1 entities added"))
      val request = FakeRequest().withBody(Json.toJson(fakePaymentData))
      val result: Future[Result] = testController.createPayment().apply(request)
      status(result) must be equalTo 200
      (contentAsJson(result) \ "message").as[String] must be equalTo "1 entities added"
      there was one(mockPaymentService).addPayments(any[List[Payment]])
    }
  }

  "Payments#create" should {
    "requires all mandatory fields" in {
      val request = FakeRequest().withBody(Json.toJson(fakePaymentErrorData))
      val result: Future[Result] = testController.createPayment().apply(request)
      status(result) must be equalTo 400
    }
  }

  "Payments#create" should {
    "If something goes wrong with Database" in {
      val errorMessage = new ErrorMessage("SQL Exception: Connection to database failure", "database_error")
      mockPaymentService.addPayments(any[List[Payment]]) returns Future(Left(errorMessage))
      val request = FakeRequest().withBody(Json.toJson(fakePaymentData))
      val result: Future[Result] = testController.createPayment().apply(request)
      status(result) must be equalTo 500
    }
  }
}