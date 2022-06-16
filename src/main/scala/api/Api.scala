package api

import message.Message
import subscribe.Subscriber

trait Api {
  def send(msg: Message, receiver: Subscriber): Unit
}
