package dao

import com.google.inject.ImplementedBy
import models.Invoice
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@ImplementedBy(classOf[InvoiceDAOImpl])
trait InvoiceDAO {
  def add(invoices: List[Invoice]): Future[Either[Exception, String]]
}