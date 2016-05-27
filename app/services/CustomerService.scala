package services

import scala.Left
import scala.Right
import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.google.inject.ImplementedBy
import com.google.inject.Singleton

import dao.CustomerDAO
import dao.CustomerDAOImpl
import javax.inject.Inject
import models.Customer
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import utilities.ErrorMessage

/**
 *
 * Created by Anisha Sampath Kumar
 */
@Singleton
class CustomerService @Inject() (customerDAO: CustomerDAO, invoiceService: InvoiceService,
                                 implicit val ec: ExecutionContext) {

  def addCustomers(customer: List[Customer]): Future[Either[ErrorMessage, String]] = {   
    customerDAO.add(customer)
  }

  def getAllCustomerData(customerId: String): Future[Either[ErrorMessage, JsObject]] = {

    val result = customerDAO.get(customerId)
    result.flatMap(result => {
      result match {
        case Right(customer: Customer) => {
          var customerObject = Json.toJson(customer).as[JsObject]
          invoiceService.getInvoicesByCustomer(customerId).map(result => {
            if ((result != null) && (!result.isEmpty))
              customerObject = customerObject + ("invoices", Json.toJson(result))
            Right(customerObject)
          })
        }
        case Left(error: ErrorMessage) => Future(Left(error))
      }
    })
  }

}