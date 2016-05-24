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
 * Mapped payments table object.
 */
class PaymentsTable(tag: Tag) extends Table[Payment](tag, "f_payments") {

  def payment_id = column[String]("payment_id", O.PrimaryKey)
  def invoice_id = column[String]("invoice_id")
  def payment_datetime = column[DateTime]("payment_datetime")
  def payment_type = column[Option[String]]("payment_type")

  def * =
    (payment_id, invoice_id, payment_datetime, payment_type) <> ((Payment.apply _).tupled, Payment.unapply)
}