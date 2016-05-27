package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.json.Reads.StringReads
import play.api.libs.json.Reads.applicative
import play.api.libs.json.Reads.functorReads
import play.api.libs.json.Reads.minLength
import play.api.libs.json.Writes
import play.api.libs.json.Writes.StringWrites

/**
 *
 * Created by Anisha Sampath Kumar
 */

/**
 * Customer entity.
 *
 * @param id        unique id
 * @param firstName first name
 * @param lastName  last name
 */
case class Customer(
  customer_id: String,
  customer_firstname: String,
  customer_lastname: Option[String],
  customer_email: Option[String],
  customer_phone: Option[String],
  customer_address: Option[String],
  customer_zipcode: Option[String],
  customer_city: Option[String],
  customer_country: Option[String])

object Customer {
  implicit val customerReads: Reads[Customer] = (
    (JsPath \ "customer_id").read[String](minLength[String](1)) and
    (JsPath \ "customer_firstname").read[String](minLength[String](1)) and
    (JsPath \ "customer_lastname").readNullable[String] and
    (JsPath \ "customer_email").readNullable[String] and
    (JsPath \ "customer_phone").readNullable[String] and
    (JsPath \ "customer_address").readNullable[String] and
    (JsPath \ "customer_zipcode").readNullable[String] and
    (JsPath \ "customer_city").readNullable[String] and
    (JsPath \ "customer_country").readNullable[String])(Customer.apply _)

  implicit val customerWrites: Writes[Customer] = (
    (JsPath \ "customer_id").write[String] and
    (JsPath \ "customer_firstname").write[String] and
    (JsPath \ "customer_lastname").writeNullable[String] and
    (JsPath \ "customer_email").writeNullable[String] and
    (JsPath \ "customer_phone").writeNullable[String] and
    (JsPath \ "customer_address").writeNullable[String] and
    (JsPath \ "customer_zipcode").writeNullable[String] and
    (JsPath \ "customer_city").writeNullable[String] and
    (JsPath \ "customer_country").writeNullable[String])(unlift(Customer.unapply _))

}

