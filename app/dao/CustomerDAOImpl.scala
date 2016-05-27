package dao

import java.sql.SQLException

import scala.Left
import scala.Right
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.google.inject.ImplementedBy

import javax.inject.Inject
import models.Customer
import models.CustomersTable
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api.TableQuery
import slick.driver.MySQLDriver.api.columnExtensionMethods
import slick.driver.MySQLDriver.api.queryInsertActionExtensionMethods
import slick.driver.MySQLDriver.api.streamableQueryActionExtensionMethods
import slick.driver.MySQLDriver.api.stringColumnType
import slick.driver.MySQLDriver.api.valueToConstColumn
import utilities.ErrorMessage
import utilities.ErrorType

/**
 * Created by Anisha Sampath Kumar
 */

/**
 * Data access class for Customer
 */
class CustomerDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends CustomerDAO {
  
  //database configurations
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  //query for customer table
  val customerQuery = TableQuery[CustomersTable]

  /**
   * adds List of Customers to database
   * 
   * @param customers list of customers
   * @return future of either success message string or error message 
   */
  override def add(customers: List[Customer]): Future[Either[ErrorMessage, String]] = {
    val result = dbConfig.db.run(customerQuery ++= customers)
    result.map(res => Right(customers.size + " Customer(s) added")).recover {
      case ex: SQLException => Left(new ErrorMessage(ex.getCause.getMessage, ErrorType.Database_Error))
    }
  }
  
  /**
   * gets customer data from database for given customer id
   * 
   * @param customerId customer id
   * @return future of either customer or not found error message
   */
  override def get(customerId: String): Future[Either[ErrorMessage, Customer]] = {
    val result = dbConfig.db.run(customerQuery.filter(_.customer_id === customerId).result.headOption)
    result.map(customer => customer match {
      case Some(customer: Customer) => Right(customer)
      case _ => Left(new ErrorMessage("Customer id not found", ErrorType.Not_Found))
    })
  }
}