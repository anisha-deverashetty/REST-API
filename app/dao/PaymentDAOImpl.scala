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

/**
 *
 * Created by Anisha Sampath Kumar
 */
class PaymentDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends PaymentDAO {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val paymentQuery = TableQuery[PaymentsTable]

  override def add(payments: List[Payment]): Future[Either[ErrorMessage, String]] = {

    dbConfig.db.run(paymentQuery ++= payments).map(res => Right(payments.size + " entities added")).recover {
      case ex: SQLException => Left(new ErrorMessage(ex.getCause.getMessage, "database_error"))
    }
  }

  override def getByInvoiceId(invoiceId: String): Future[Option[Payment]] = {

    dbConfig.db.run(paymentQuery.filter(_.invoice_id === invoiceId).result.headOption)
    
  }

}