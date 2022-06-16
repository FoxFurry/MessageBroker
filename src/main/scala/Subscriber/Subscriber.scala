package Subscriber

import Message.Message

import scala.util.{Failure, Success, Try}

class Subscriber(_address: String, _topic: String, _api: Api.Api){
  private var cursor: Int = 0
  private val address: String = _address
  private val api: Api.Api = _api

  val topic: String = _topic

  def send(msg: Message): Unit =
    sendNonWrapped(msg) match {
      case Success(_) =>
        cursor += 1
        println(s"[Subscriber: ${address}, ${topic}] Successfully sent message. New cursor position: ${cursor}")

      case Failure(exception) =>
        print(s"[Subscriber: ${address}, ${topic}] Failed to send message. $exception")

    }

  def getCursor: Int = { cursor }

  private def sendNonWrapped(msg: Message): Try[Unit] = Try {
    try {
      api.send(msg, this)
    } catch {
      case e: Api.ApiException => throw SubscriberSendException(e.toString)
    }
  }
}
