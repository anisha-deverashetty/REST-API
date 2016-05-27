package models

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import play.api.db.slick.DatabaseConfigProvider

/**
 * Created by Anisha Sampath Kumar
 */

/**
 * Mapped customers table object
 * 
 */
class CustomersTable(tag: Tag) extends Table[Customer](tag, "d_customers") {

  def customer_id = column[String]("customer_id", O.PrimaryKey)
  def customer_firstname = column[String]("customer_firstname")
  def customer_lastname = column[Option[String]]("customer_lastname")
  def customer_email = column[Option[String]]("customer_email")
  def customer_phone = column[Option[String]]("customer_phone")
  def customer_address = column[Option[String]]("customer_address")
  def customer_zipcode = column[Option[String]]("customer_zipcode")
  def customer_city = column[Option[String]]("customer_city")
  def customer_country = column[Option[String]]("customer_country")

  def * =
    (customer_id, customer_firstname, customer_lastname, customer_email,
      customer_phone, customer_address, customer_zipcode,
      customer_city, customer_country) <> ((Customer.apply _).tupled, Customer.unapply)
}