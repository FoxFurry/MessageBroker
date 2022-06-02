package Subscriber

import Message.Message

import scala.util.Try

class Subscriber(_address: String, _api: Api.Api){
  val address: String = _address
  val api: Api.Api = _api

  def send(msg: Message): Try[Unit] = Try(
    sendNonWrapped(msg)
  )

  private def sendNonWrapped(msg: Message): Unit = {
    try {
      api.send(msg, this)
    } catch {
      case e: Api.ApiException => throw SubscriberSendException(e.toString)
    }
  }
}
