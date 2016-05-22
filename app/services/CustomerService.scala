package services

import javax.inject.Inject
import com.google.inject.Singleton
import dao.CustomerDAO
import models.Customer
import scala.concurrent.Future

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class CustomerService @Inject() (customerDAO: CustomerDAO) {
  def addCustomers(customer: List[Customer]): Future[Either[Exception, String]] = {
    customerDAO.add(customer)
  }
}