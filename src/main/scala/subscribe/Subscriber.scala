package subscribe

import message.Message

import scala.util.{Failure, Success, Try}

class Subscriber(_address: String, _topic: String, _api: api.Api){
  private var cursor: Int = 0
  private val api: api.Api = _api

  val address: String = _address
  val topic: String = _topic

  def getCursor: Int = { cursor }

  def send(msg: Message): Unit =
    sendNonWrapped(msg) match {
      case Success(_) =>
        cursor += 1
        println(s"[Subscriber: ${address}, ${topic}] Successfully sent message. New cursor position: ${cursor}")

      case Failure(exception) =>
        print(s"[Subscriber: ${address}, ${topic}] Failed to send message. $exception")

    }

  private def sendNonWrapped(msg: Message): Try[Unit] = Try {
//    try {
//      api.send(msg, this)
//    } catch {
//      case e: Api.ApiException => throw SubscriberSendException(e.toString)
//    }
  }
}
