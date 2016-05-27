package models

import org.joda.time.DateTime

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.json.Reads.StringReads
import play.api.libs.json.Reads.applicative
import play.api.libs.json.Reads.bigDecReads
import play.api.libs.json.Reads.functorReads
import play.api.libs.json.Reads.minLength
import play.api.libs.json.Writes

/**
 * Created by Anisha Sampath Kumar
 */

//Invoice entity
case class Invoice(
  invoice_id: String,
  customer_id: String,
  net_amount: Option[BigDecimal],
  vat_amount: Option[BigDecimal],
  total_amount: BigDecimal,
  invoice_datetime: DateTime,
  warehouse_id: Option[String],
  supplier_id: Option[String],
  salesperson_id: Option[String])

object Invoice {

  //for converting from JsValue to Invoice
  implicit val invoiceReads: Reads[Invoice] = (
    (JsPath \ "invoice_id").read[String](minLength[String](1)) and
    (JsPath \ "customer_id").read[String](minLength[String](1)) and
    (JsPath \ "net_amount").readNullable[BigDecimal] and
    (JsPath \ "vat_amount").readNullable[BigDecimal] and
    (JsPath \ "total_amount").read[BigDecimal] and
    (JsPath \ "invoice_datetime").read[DateTime](Reads.jodaDateReads("yyyy-MM-dd HH:mm:ss")) and
    (JsPath \ "warehouse_id").readNullable[String] and
    (JsPath \ "supplier_id").readNullable[String] and
    (JsPath \ "salesperson_id").readNullable[String])(Invoice.apply _)

    //for converting Invoice to JsValue
    implicit val invoiceWrites: Writes[Invoice] = (
    (JsPath \ "invoice_id").write[String] and
    (JsPath \ "customer_id").write[String] and
    (JsPath \ "net_amount").writeNullable[BigDecimal] and
    (JsPath \ "vat_amount").writeNullable[BigDecimal] and
    (JsPath \ "total_amount").write[BigDecimal] and
    (JsPath \ "invoice_datetime").write[DateTime](Writes.jodaDateWrites("yyyy-MM-dd HH:mm:ss")) and
    (JsPath \ "warehouse_id").writeNullable[String] and
    (JsPath \ "supplier_id").writeNullable[String] and
    (JsPath \ "salesperson_id").writeNullable[String])(unlift(Invoice.unapply _))
}

