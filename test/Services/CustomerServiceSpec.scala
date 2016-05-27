package test

import scala.Right
import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import com.google.inject.ImplementedBy
import com.google.inject.Singleton

import dao.CustomerDAO
import dao.CustomerDAOImpl
import models.Customer
import services.CustomerService
import services.InvoiceService


@RunWith(classOf[JUnitRunner])
class CustomerServiceSpec extends Specification with Mockito {

  val mockCustomerDAO = mock[CustomerDAO]
  val mockCustomerService = new CustomerService(mockCustomerDAO, mock[InvoiceService], mock[ExecutionContext])
  val customer = new Customer("11", "Steve", Option("John"), Option("user@example.org"), Option("15125"), 
      Option("helsinki"), Option("00100"), Option("Helsinki"), Option("Finland"))

  "CustomerService#addCustomers" should {
    "return 1 Customer(s) added" in {
      mockCustomerDAO.add(any[List[Customer]]) returns Future(Right("1 Customer(s) added"))
      val result = mockCustomerService.addCustomers(List(customer))
      result.map(result =>
        result match {
          case Right(result: String) => result must be equalTo "1 Customer(s) added"
          case Left(_)               => failure
        })
      there was one(mockCustomerDAO).add(any[List[Customer]])
    }
  }
}