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
class InvoiceDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends InvoiceDAO {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val invoiceQuery = TableQuery[InvoicesTable]

  override def add(invoices: List[Invoice]): Future[Either[Exception, String]] = {

    dbConfig.db.run(invoiceQuery ++= invoices).map(res => Right(invoices.size + " entities added")).recover {
      case ex: Exception => Left(ex)
    }
  }

}