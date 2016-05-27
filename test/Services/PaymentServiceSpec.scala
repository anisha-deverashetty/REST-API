package test

import scala.Right
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import dao.PaymentDAO
import models.Payment
import services.PaymentService

@RunWith(classOf[JUnitRunner])
class PaymentServiceSpec extends Specification with Mockito {

  val mockPaymentDAO = mock[PaymentDAO]
  val mockPaymentService = new PaymentService(mockPaymentDAO)
  val payment = Payment("P1001", "in1001", DateTime.now, Option("Card"))

  "PaymentService#addPayments" should {
    "return 1 Payment(s) added" in {
      mockPaymentDAO.add(any[List[Payment]]) returns Future(Right("1 Payment(s) added"))
      val result = mockPaymentService.addPayments(List(payment))
      result.map(result =>
        result match {
          case Right(result: String) => result must be equalTo "1 Payment(s) added"
          case Left(_)               => failure
        })
      there was one(mockPaymentDAO).add(any[List[Payment]])
    }
  }
}