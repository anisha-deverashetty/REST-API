package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import slick.driver.JdbcProfile
import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 *
 * Created by Anisha Sampath Kumar
 */

/**
 * Payment entity.
 *
 * @param id        unique id
 */
case class Payment(
  payment_id: String,
  invoice_id: String,
  payment_datetime: DateTime,
  payment_type: Option[String])

object Payment {

  implicit val paymentReads: Reads[Payment] = (
    (JsPath \ "payment_id").read[String](minLength[String](1)) and
    (JsPath \ "invoice_id").read[String](minLength[String](1)) and
    (JsPath \ "payment_datetime").read[DateTime](Reads.jodaDateReads("yyyy-MM-dd HH:mm:ss")) and
    (JsPath \ "payment_type").readNullable[String])(Payment.apply _)

}

