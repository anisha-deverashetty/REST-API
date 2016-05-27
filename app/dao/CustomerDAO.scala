package dao

import scala.concurrent.Future

import com.google.inject.ImplementedBy

import models.Customer
import utilities.ErrorMessage

/**
 * Created by Anisha Sampath Kumar
 */

/**
 *  Holds data access methods for Customer 
 */
@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {

  def add(customers: List[Customer]): Future[Either[ErrorMessage, String]]

  def get(customerId: String): Future[Either[ErrorMessage, Customer]]

}