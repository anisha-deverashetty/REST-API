package dao

import com.google.inject.ImplementedBy
import models.Payment
import scala.concurrent.Future

/**
  * 
  * Created by Anisha Sampath Kumar
  */
@ImplementedBy(classOf[PaymentDAOImpl])
trait PaymentDAO {

  def add(payments : List[Payment]): Future[Either[Exception, String]]
 
}