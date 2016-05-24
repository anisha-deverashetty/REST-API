package services

import play.api.libs.json._
import javax.inject.Inject
import com.google.inject.Singleton
import dao.CustomerDAO
import models.Customer
import models.Invoice
import models.Payment
import utilities._
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

  def getAllCustomerData(customerId: String): Future[Either[ErrorMessage, JsObject]] = {

    val result = customerDAO.get(customerId)
    result.flatMap(result =>
      result match {
        case Right(customer: Customer) => {
          var customerObject = Json.toJson(customer).as[JsObject]
          invoiceService.getInvoicesByCustomer(customerId).map(result => {
            if (!result.isEmpty)
              customerObject = customerObject + ("invoices", Json.toJson(result))
            println("customers : " + customerObject)
            Right(customerObject)
          })
        }
        case Left(error: ErrorMessage) => Future(Left(error))
      })
  }

}