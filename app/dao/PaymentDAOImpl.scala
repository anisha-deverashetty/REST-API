package dao

import java.sql.SQLException

import scala.Left
import scala.Right
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.google.inject.ImplementedBy

import javax.inject.Inject
import models.Payment
import models.PaymentsTable
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
 * Data access class for Payment
 */
class PaymentDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends PaymentDAO {
  
  //database configurations
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  //query for payment table
  val paymentQuery = TableQuery[PaymentsTable]

  /**
   * adds List of Payments to database
   * 
   * @param payments list of payments
   * @return future of either success message string or error message 
   */
  override def add(payments: List[Payment]): Future[Either[ErrorMessage, String]] = {
    dbConfig.db.run(paymentQuery ++= payments).map(res => Right(payments.size + " Payment(s) added")).recover {
      case ex: SQLException => Left(new ErrorMessage(ex.getCause.getMessage, ErrorType.Database_Error))
    }
  }
}