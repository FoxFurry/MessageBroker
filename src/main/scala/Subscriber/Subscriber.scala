package Subscriber

import Message.Message

import scala.util.Try

class Subscriber(_address: String, _api: API.API){
  val address: String = _address
  val api: API.API = _api

  def send(msg: Message): Try[Unit] = Try(
    sendNonWrapped(msg)
  )

  private def sendNonWrapped(msg: Message): Unit = {
    try {
      api.send(msg, this)
    } catch {
      case e: API.APIException => throw SubscriberSendException(e.toString)
    }
  }
}
