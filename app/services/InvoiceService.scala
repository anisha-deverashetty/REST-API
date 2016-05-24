package services

import play.api.libs.json._
import javax.inject.Inject
import com.google.inject.Singleton
import dao.InvoiceDAO
import models.Invoice
import models.Payment
import models.ErrorMessage
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

  def getInvoicesByCustomer(customerId: String): Seq[JsObject] = {

    val invoices = invoiceDAO.getByCustomerId(customerId)

    var invoiceObj = Seq[JsObject]()

    invoices.map(invoices => {
      val futures = for (i <- invoices) yield {
        getInvoiceWithPayment(i)
      }
      //Seq[Future[JsObject]]
      Future.sequence(futures).map {
        results => return results
      }.recover {
        case e: Exception => return invoiceObj
      }
      invoiceObj
    })
  }

  def getInvoiceWithPayment(invoice: Invoice): Future[JsObject] = {

    val payment = paymentService.getPaymentByInvoiceID(invoice.invoice_id)
    payment.map(payment =>
      payment match {
        case Some(payment: Payment) => {
          var jsonObject = Json.toJson(invoice).as[JsObject]
          jsonObject = jsonObject + ("Payment", Json.toJson(payment))
          println("Payments : " + jsonObject)
          //Thread.sleep(5000)
          jsonObject
        }
        case _ => Json.toJson(invoice).as[JsObject]
      })
  }

}