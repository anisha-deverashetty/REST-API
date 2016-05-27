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
 * Created by Anisha Sampath Kumar
 */

/**
 * Data access class for Invoice
 */
class InvoiceDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends InvoiceDAO {

  //database configurations
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  //query for invoice table
  val invoiceQuery = TableQuery[InvoicesTable]
  
  //query for payment table 
  val paymentQuery = TableQuery[PaymentsTable]

  /**
   * adds List of Invoices to database
   * 
   * @param invoices list of invoices
   * @return future of either success message string or error message 
   */
  override def add(invoices: List[Invoice]): Future[Either[ErrorMessage, String]] = {
    dbConfig.db.run(invoiceQuery ++= invoices).map(res => Right(invoices.size + " Invoice(s) added")).recover {
      case ex: SQLException => Left(new ErrorMessage(ex.getCause.getMessage, ErrorType.Database_Error))
    }
  }
  
  /**
   * gets invoices with payment data for given customer id
   * 
   * @param customerId customer id
   * @return future of invoice+payment sequence 
   */
  override def getInvoicesWithPayment(customerId: String): Future[Seq[JsObject]] = {
    
   //combines two queries into single query with left outer join and 
   //yields required rows of invoice table in i and payment table in p  
    val leftOuterJoin = for {
      (i, p) <- invoiceQuery.filter(_.customer_id === customerId) joinLeft paymentQuery on (_.invoice_id === _.invoice_id)
    } yield (i, p)

    dbConfig.db.run(leftOuterJoin.result).map(row => row.map {
      case (i, p) => {
        var invoiceObj = Json.toJson(Invoice(i.invoice_id, i.customer_id, i.net_amount, i.vat_amount, 
            i.total_amount, i.invoice_datetime, i.warehouse_id, i.supplier_id, i.salesperson_id)).as[JsObject]
        p match {
          case Some(p: Payment) => {
            val payment = Payment(p.payment_id, p.invoice_id, p.payment_datetime, p.payment_type)
            invoiceObj = invoiceObj + ("payment", Json.toJson(payment))
            invoiceObj
          }
          case _ => invoiceObj
        }
      }
    })
  }
}
