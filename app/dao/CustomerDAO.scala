package dao

import com.google.inject.ImplementedBy
import models.Customer
import models.ErrorMessage
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {

  def add(customers: List[Customer]): Future[Either[ErrorMessage, String]]
  
  def get(customerId: String): Future[Either[ErrorMessage, Customer]]
  
  
}