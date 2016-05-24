package services

import play.api.libs.json._
import javax.inject.Inject
import com.google.inject.Singleton
import dao.InvoiceDAO
import models.Invoice
import models.Payment
import utilities._
import scala.concurrent.{ Future, ExecutionContext }
import scala.concurrent.blocking
import scala.util.{ Success, Failure }

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
  /*
  def getInvoicesByCustomerID(customerId: String): Future[Seq[Invoice]]] = {
    invoiceDAO.getByCustomerId(customerId)
  }
*/

  def getInvoicesByCustomer(customerId: String): Future[Seq[JsObject]] = {

    val invoices = invoiceDAO.getByCustomerId(customerId)
    invoices.flatMap(invoices => {
      val futures = for (invoice <- invoices) yield {
        getInvoiceWithPayment(invoice)
      }
      Future.sequence(futures).map { results => results
      }.recover {
        case e: Exception => Seq[JsObject]()
      }
    })
  }

  def getInvoiceWithPayment(invoice: Invoice): Future[JsObject] = {

    var invoiceObj = Json.toJson(invoice).as[JsObject]
    val payment = paymentService.getPaymentByInvoice(invoice.invoice_id)   
    payment.map(payment =>
      payment match {
        case Some(payment: Payment) => {          
          invoiceObj = invoiceObj + ("payment", Json.toJson(payment))
          //println("Payments : " + jsonObject)
          invoiceObj
        }
        case _ => invoiceObj
      })
  }

}