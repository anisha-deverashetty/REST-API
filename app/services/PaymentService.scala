package services

import scala.concurrent.Future

import com.google.inject.ImplementedBy
import com.google.inject.Singleton

import dao.PaymentDAO
import dao.PaymentDAOImpl
import javax.inject.Inject
import models.Payment
import utilities.ErrorMessage

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class PaymentService @Inject() (paymentDAO: PaymentDAO) {
  
  def addPayments(payments: List[Payment]): Future[Either[ErrorMessage, String]] = {
    paymentDAO.add(payments)
  }
  
}