package services

import javax.inject.Inject
import com.google.inject.Singleton
import dao.PaymentDAO
import models.Payment
import utilities._
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class PaymentService @Inject() (paymentDAO: PaymentDAO) {
  def addPayments(payments: List[Payment]): Future[Either[ErrorMessage, String]] = {
    paymentDAO.add(payments)
  }
  def getPaymentByInvoice(invoiceId: String): Future[Option[Payment]] = {
    paymentDAO.getByInvoiceId(invoiceId)
  }
  
  
}