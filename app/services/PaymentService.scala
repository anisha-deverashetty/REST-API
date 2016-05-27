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
 * Created by Anisha Sampath Kumar
 */

/**
 * Service class to access PaymentDAO
 */
@Singleton
class PaymentService @Inject() (paymentDAO: PaymentDAO) {
  
  /**
   * sends List of Payments to PaymentDAO
   * 
   * @param payments list of payments
   * @return future of either success message string or error message 
   */
  def addPayments(payments: List[Payment]): Future[Either[ErrorMessage, String]] = {
    paymentDAO.add(payments)
  }
  
}