package dao

import com.google.inject.ImplementedBy
import models.Customer
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {

  def add(customers: List[Customer]): Future[Either[Exception, String]]
}