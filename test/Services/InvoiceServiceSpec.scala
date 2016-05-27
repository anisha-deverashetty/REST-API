package test

import scala.Right
import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.math.BigDecimal.double2bigDecimal
import scala.math.BigDecimal.int2bigDecimal

import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import com.google.inject.Singleton

import dao.InvoiceDAO
import javax.inject.Inject
import models.Invoice
import play.api.libs.json.Json
import services.InvoiceService
import services.PaymentService
import utilities.ErrorMessage

@RunWith(classOf[JUnitRunner])
class InvoiceServiceSpec extends Specification with Mockito {

  val mockInvoiceDAO = mock[InvoiceDAO]
  val mockInvoiceService = new InvoiceService(mockInvoiceDAO, mock[PaymentService], mock[ExecutionContext])

  val invoice = Invoice("in1001", "c_01", Option(10), Option(2.9), 12.9, 
      DateTime.now, Option("W_1"), Option("S_1"), Option("SP_1"))

  "InvoiceService#addInvoices" should {
    "return 1 Invoices(s) added" in {
      mockInvoiceDAO.add(any[List[Invoice]]) returns Future(Right("1 Invoice(s) added"))
      val result = mockInvoiceService.addInvoices(List(invoice))
      result.map(result =>
        result match {
          case Right(result: String) => result must be equalTo "1 Invoice(s) added"
          case Left(_)               => failure
        })
      there was one(mockInvoiceDAO).add(any[List[Invoice]])
    }
  }
  
  "InvoiceService#getInvoicesByCustomer" should {
    "return empty sequence for no invoice" in {
      val testSeq = Seq(Json.obj())
      mockInvoiceDAO.getInvoicesWithPayment(any[String]) returns Future(testSeq)
      val result = mockInvoiceService.getInvoicesByCustomer("c_01")
      result.map(result =>{
        result must be equalTo testSeq
      })
      there was one(mockInvoiceDAO).getInvoicesWithPayment(any[String])
    }
  }
  
}