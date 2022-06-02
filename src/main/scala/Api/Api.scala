package API

import Message.Message
import Subscriber.Subscriber

trait API {
  def send(msg: Message, receiver: Subscriber): Unit
}
