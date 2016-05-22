package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
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
 * Invoice entity.
 *
 * @param id        unique id
 */
case class Invoice(
  invoice_id: String,
  line_item_id: String,
  customer_id: String,
  product_id: String,
  product_quantity: BigDecimal,
  net_amount: Option[BigDecimal],
  vat_amount: Option[BigDecimal],
  total_amount: BigDecimal,
  invoice_datetime: DateTime,
  warehouse_id: Option[String],
  supplier_id: Option[String],
  salesperson_id: Option[String],
  cancel_id: Option[String])

object Invoice {

  implicit val invoiceReads: Reads[Invoice] = (
    (JsPath \ "invoice_id").read[String](minLength[String](1)) and
    (JsPath \ "line_item_id").read[String](minLength[String](1)) and
    (JsPath \ "customer_id").read[String](minLength[String](1)) and
    (JsPath \ "product_id").read[String](minLength[String](1)) and
    (JsPath \ "product_quantity").read[BigDecimal] and
    (JsPath \ "net_amount").readNullable[BigDecimal] and
    (JsPath \ "vat_amount").readNullable[BigDecimal] and
    (JsPath \ "total_amount").read[BigDecimal] and
    (JsPath \ "invoice_datetime").read[DateTime](Reads.jodaDateReads("yyyy-MM-dd HH:mm:ss")) and
    (JsPath \ "warehouse_id").readNullable[String] and
    (JsPath \ "supplier_id").readNullable[String] and
    (JsPath \ "salesperson_id").readNullable[String] and
    (JsPath \ "cancel_id").readNullable[String])(Invoice.apply _)
}

