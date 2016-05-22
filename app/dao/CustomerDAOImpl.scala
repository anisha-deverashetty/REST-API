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
class CustomerDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends CustomerDAO {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val customerQuery = TableQuery[CustomersTable]

  override def add(customers: List[Customer]): Future[Either[Exception, String]] = {

    dbConfig.db.run(customerQuery ++= customers).map(res => Right(customers.size + " entities added")).recover {
      case ex: Exception => Left(ex)
    }
  }

}