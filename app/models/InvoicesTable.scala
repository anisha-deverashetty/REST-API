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

  def invoice_id = column[String]("invoice_id")
  def line_item_id = column[String]("line_item_id")
  def customer_id = column[String]("customer_id")
  def product_id = column[String]("product_id")
  def product_quantity = column[BigDecimal]("product_quantity")
  def net_amount = column[Option[BigDecimal]]("net_amount")
  def vat_amount = column[Option[BigDecimal]]("vat_amount")
  def total_amount = column[BigDecimal]("total_amount")
  def invoice_datetime = column[DateTime]("invoice_datetime")
  def warehouse_id = column[Option[String]]("warehouse_id")
  def supplier_id = column[Option[String]]("supplier_id")
  def salesperson_id = column[Option[String]]("salesperson_id")
  def cancel_id = column[Option[String]]("cancel_id")

  def * =
    (invoice_id, line_item_id, customer_id, product_id, product_quantity, net_amount, vat_amount,
      total_amount, invoice_datetime, warehouse_id, supplier_id,
      salesperson_id, cancel_id) <> ((Invoice.apply _).tupled, Invoice.unapply)
}