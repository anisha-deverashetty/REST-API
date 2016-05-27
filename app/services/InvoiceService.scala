package services

import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.google.inject.ImplementedBy
import com.google.inject.Singleton

import dao.InvoiceDAO
import dao.InvoiceDAOImpl
import javax.inject.Inject
import models.Invoice
import play.api.libs.json.JsObject
import utilities.ErrorMessage

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class InvoiceService @Inject() (invoiceDAO: InvoiceDAO, paymentService: PaymentService,
                                implicit val ec: ExecutionContext) {
  def addInvoices(invoices: List[Invoice]): Future[Either[ErrorMessage, String]] = {
    invoiceDAO.add(invoices)
  }

  def getInvoicesByCustomer(customerId: String): Future[Seq[JsObject]] = {
    invoiceDAO.getInvoicesWithPayment(customerId)
  }
}