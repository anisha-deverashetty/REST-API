package dao

import com.google.inject.ImplementedBy
import models.Payment
import scala.concurrent.Future
import utilities._

/**
 *
 * Created by Anisha Sampath Kumar
 */
@ImplementedBy(classOf[PaymentDAOImpl])
trait PaymentDAO {

  def add(payments: List[Payment]): Future[Either[ErrorMessage, String]]
  def getByInvoiceId(invoiceId: String): Future[Option[Payment]]
}