package models

import org.joda.time.DateTime

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.json.Reads.StringReads
import play.api.libs.json.Reads.applicative
import play.api.libs.json.Reads.functorReads
import play.api.libs.json.Reads.minLength
import play.api.libs.json.Writes

/**
 * Created by Anisha Sampath Kumar
 */

//Payment entity
case class Payment(
  payment_id: String,
  invoice_id: String,
  payment_datetime: DateTime,
  payment_type: Option[String])

object Payment {

  //for converting from JsValue to Payment
  implicit val paymentReads: Reads[Payment] = (
    (JsPath \ "payment_id").read[String](minLength[String](1)) and
    (JsPath \ "invoice_id").read[String](minLength[String](1)) and
    (JsPath \ "payment_datetime").read[DateTime](Reads.jodaDateReads("yyyy-MM-dd HH:mm:ss")) and
    (JsPath \ "payment_type").readNullable[String])(Payment.apply _)

  //for converting Payment to JsValue  
  implicit val paymentWrites: Writes[Payment] = (
    (JsPath \ "payment_id").write[String] and
    (JsPath \ "invoice_id").write[String] and
    (JsPath \ "payment_datetime").write[DateTime](Writes.jodaDateWrites("yyyy-MM-dd HH:mm:ss")) and
    (JsPath \ "payment_type").writeNullable[String])(unlift(Payment.unapply _))

}

