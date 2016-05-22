package services

import javax.inject.Inject
import com.google.inject.Singleton
import dao.PaymentDAO
import models.Payment
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class PaymentService @Inject() (paymentDAO: PaymentDAO) {
  def addPayments(payments: List[Payment]): Future[Either[Exception, String]] = {
    paymentDAO.add(payments)
  }
}