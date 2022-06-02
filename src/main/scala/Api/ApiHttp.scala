package Api

import Message.Message
import Subscriber.Subscriber
import sttp.client3._
import sttp.model.StatusCode

class ApiHttp extends Api {
  private val backend = HttpClientSyncBackend()

  def send(msg: Message, receiver: Subscriber): Unit = {
    val request = basicRequest.body(Map("message" -> msg.data)).post(uri"${receiver.address}")
    val response = request.send(backend)

    if (response.code != StatusCode.Ok) {
      throw ApiException(s"${response.statusText}:${response.body}")
    }
  }
}
