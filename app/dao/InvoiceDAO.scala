package dao

import scala.concurrent.Future

import com.google.inject.ImplementedBy

import models.Invoice
import play.api.libs.json.JsObject
import utilities.ErrorMessage

/**
 * Created by Anisha Sampath Kumar
 */

/**
 * Holds data access methods for Invoice
 */
@ImplementedBy(classOf[InvoiceDAOImpl])
trait InvoiceDAO {
  def add(invoices: List[Invoice]): Future[Either[ErrorMessage, String]]

  def getInvoicesWithPayment(customerId: String): Future[Seq[JsObject]]
}