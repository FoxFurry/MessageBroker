package Subscriber

import Message.Message

import scala.util.Try

final case class SubscriberSendException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)

class Subscriber(_address: String, _subType: SubscriberTypes.SubscriberType){
  val address: String = _address
  val subType: SubscriberTypes.SubscriberType = _subType

  def send(msg: Message): Try[Unit] = Try(
    sendNonWrapped(msg)
  )

  private def sendNonWrapped(msg: Message): Unit = {
    subType match {
      case SubscriberTypes.HTTP =>
        throw SubscriberSendException(s"HTTP not implemented")
      case SubscriberTypes.MQTT =>
        throw SubscriberSendException(s"MQTT not implemented")
      case SubscriberTypes.XMPP =>
        throw SubscriberSendException(s"XMPP not implemented")
      case _ =>
        throw SubscriberSendException(s"Unknown subscriber type: $subType")
    }
  }
}
