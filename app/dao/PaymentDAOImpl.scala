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

  override def add(payments : List[Payment]): Future[Either[Exception, String]] = {

    dbConfig.db.run(paymentQuery ++= payments).map(res => Right(payments.size + " entities added")).recover {
      case ex: Exception => Left(ex)
    }
  }

}