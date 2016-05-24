package services

import play.api.libs.json._
import javax.inject.Inject
import com.google.inject.Singleton
import dao.CustomerDAO
import models.Customer
import models.Invoice
import models.Payment
import models.ErrorMessage
import scala.concurrent.{ Future, ExecutionContext }

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class CustomerService @Inject() (customerDAO: CustomerDAO, invoiceService: InvoiceService,
                                 paymentService: PaymentService, implicit val ec: ExecutionContext) {

  def addCustomers(customer: List[Customer]): Future[Either[ErrorMessage, String]] = {
    customerDAO.add(customer)
  }

  def getAllCustomerData(customerId: String): Future[Either[ErrorMessage, Customer]] = {

    val result = customerDAO.get(customerId)
    result.map(result =>
      result match {
        case Right(customer: Customer) => {
          invoiceService.getInvoicesByCustomer(customerId).map(res => println("arr : " + res))
          Right(customer)
        }
        case Left(error: ErrorMessage) => Left(error)
      })
  }

  

  
}