package models

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import play.api.db.slick.DatabaseConfigProvider
import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 *
 * Created by Anisha Sampath Kumar
 */


/**
 * Mapped invoices table object.
 */
class InvoicesTable(tag: Tag) extends Table[Invoice](tag, "f_invoices") {

  def invoice_id = column[String]("invoice_id", O.PrimaryKey)
  def customer_id = column[String]("customer_id")
  def net_amount = column[Option[BigDecimal]]("net_amount")
  def vat_amount = column[Option[BigDecimal]]("vat_amount")
  def total_amount = column[BigDecimal]("total_amount")
  def invoice_datetime = column[DateTime]("invoice_datetime")
  def warehouse_id = column[Option[String]]("warehouse_id")
  def supplier_id = column[Option[String]]("supplier_id")
  def salesperson_id = column[Option[String]]("salesperson_id")

  def * =
    (invoice_id, customer_id, net_amount, vat_amount,
      total_amount, invoice_datetime, warehouse_id, supplier_id,
      salesperson_id) <> ((Invoice.apply _).tupled, Invoice.unapply)
}