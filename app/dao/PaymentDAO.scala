package dao

import scala.concurrent.Future

import com.google.inject.ImplementedBy

import models.Payment
import utilities.ErrorMessage

/**
 *
 * Created by Anisha Sampath Kumar
 */
@ImplementedBy(classOf[PaymentDAOImpl])
trait PaymentDAO {

  def add(payments: List[Payment]): Future[Either[ErrorMessage, String]]

}