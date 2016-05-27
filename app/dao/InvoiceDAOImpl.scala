package dao

import java.sql.SQLException

import scala.Left
import scala.Right
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.google.inject.ImplementedBy

import javax.inject.Inject
import models.Invoice
import models.InvoicesTable
import models.Payment
import models.PaymentsTable
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api.TableQuery
import slick.driver.MySQLDriver.api.columnExtensionMethods
import slick.driver.MySQLDriver.api.queryInsertActionExtensionMethods
import slick.driver.MySQLDriver.api.streamableQueryActionExtensionMethods
import slick.driver.MySQLDriver.api.stringColumnType
import slick.driver.MySQLDriver.api.valueToConstColumn
import utilities.ErrorMessage
import utilities.ErrorType

/**
 *
 * Created by Anisha Sampath Kumar
 */
class InvoiceDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends InvoiceDAO {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val invoiceQuery = TableQuery[InvoicesTable]
  val paymentQuery = TableQuery[PaymentsTable]

  override def add(invoices: List[Invoice]): Future[Either[ErrorMessage, String]] = {
    dbConfig.db.run(invoiceQuery ++= invoices).map(res => Right(invoices.size + " Invoice(s) added")).recover {
      case ex: SQLException => Left(new ErrorMessage(ex.getCause.getMessage, ErrorType.Database_Error))
    }
  }

  override def getInvoicesWithPayment(customerId: String): Future[Seq[JsObject]] = {
    val leftOuterJoin = for {
      (c, s) <- invoiceQuery.filter(_.customer_id === customerId) joinLeft paymentQuery on (_.invoice_id === _.invoice_id)
    } yield (c, s)

    dbConfig.db.run(leftOuterJoin.result).map(row => row.map {
      case (c, s) => {
        var invoiceObj = Json.toJson(Invoice(c.invoice_id, c.customer_id, c.net_amount, c.vat_amount, 
            c.total_amount, c.invoice_datetime, c.warehouse_id, c.supplier_id, c.salesperson_id)).as[JsObject]
        s match {
          case Some(s: Payment) => {
            val payment = Payment(s.payment_id, c.invoice_id, s.payment_datetime, s.payment_type)
            invoiceObj = invoiceObj + ("payment", Json.toJson(payment))
            invoiceObj
          }
          case _ => invoiceObj
        }
      }
    })
  }
}
