package api

import message.Message
import subscribe.Subscriber
import sttp.client3._
import sttp.model.StatusCode

class ApiHttp extends Api {
  private val backend = HttpClientSyncBackend()

  def send(msg: Message, receiver: Subscriber): Unit = {
    val request = basicRequest.body(Map("topic" -> msg.topic, "data" -> msg.data)).post(uri"${receiver.address}")
    val response = request.send(backend)

    if (response.code != StatusCode.Ok) {
      throw ApiException(s"${response.statusText}:${response.body}")
    }
  }
}
