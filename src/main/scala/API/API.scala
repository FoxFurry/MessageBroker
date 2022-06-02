package API

import Message.Message
import Subscriber.Subscriber

final case class APIException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)

trait API {
  def send(msg: Message, receiver: Subscriber): Unit
}
