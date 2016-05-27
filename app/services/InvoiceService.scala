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
 * Created by Anisha Sampath Kumar
 */

/**
 * Service class to access InvoiceDAO
 */
@Singleton
class InvoiceService @Inject() (invoiceDAO: InvoiceDAO, paymentService: PaymentService,
                                implicit val ec: ExecutionContext) {
  
  /**
   * sends List of Invoices to InvoiceDAO
   * 
   * @param invoices list of invoices
   * @return future of either success message string or error message 
   */
  def addInvoices(invoices: List[Invoice]): Future[Either[ErrorMessage, String]] = {
    invoiceDAO.add(invoices)
  }
  
  /**
   * gets invoices with payment data for given customer id
   * 
   * @param customerId customer id
   * @return future of invoice+payment sequence 
   */
  def getInvoicesByCustomer(customerId: String): Future[Seq[JsObject]] = {
    invoiceDAO.getInvoicesWithPayment(customerId)
  }
}