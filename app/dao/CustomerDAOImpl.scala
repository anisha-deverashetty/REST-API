package dao

import java.sql._
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api._
import slick.driver.MySQLDriver.api._
import models._
import utilities._

/**
 *
 * Created by Anisha Sampath Kumar
 */
class CustomerDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends CustomerDAO {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val customerQuery = TableQuery[CustomersTable]

  override def add(customers: List[Customer]): Future[Either[ErrorMessage, String]] = {

    val result = dbConfig.db.run(customerQuery ++= customers)

    result.map(res => Right(customers.size + " entities added")).recover {
      case ex: SQLException => Left(new ErrorMessage(ex.getCause.getMessage, "database_error"))
    }
  }

  override def get(customerId: String): Future[Either[ErrorMessage, Customer]] = {

    val cust = dbConfig.db.run(customerQuery.filter(_.customer_id === customerId).result.headOption)

    cust.map(customer => customer match {
      case Some(customer: Customer) =>
        Right(customer)
      case _ =>
        Left(new ErrorMessage("Customer id not found", "not_found"))
    }).recover {
      case ex: Exception => Left(new ErrorMessage(ex.getCause.getMessage, "database_error"))
    }

  }

}