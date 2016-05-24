package dao

import com.google.inject.ImplementedBy
import models.Invoice
import utilities._
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@ImplementedBy(classOf[InvoiceDAOImpl])
trait InvoiceDAO {
  def add(invoices: List[Invoice]): Future[Either[ErrorMessage, String]]
  
  def getByCustomerId(customerId: String): Future[Seq[Invoice]]
}