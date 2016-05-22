package services

import javax.inject.Inject
import com.google.inject.Singleton
import dao.InvoiceDAO
import models.Invoice
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class InvoiceService @Inject() (invoiceDAO: InvoiceDAO) {
  def addInvoices(invoices: List[Invoice]): Future[Either[Exception, String]] = {
    invoiceDAO.add(invoices)
  }
}