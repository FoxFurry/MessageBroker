package Api

import Message.Message
import Subscriber.Subscriber

trait Api {
  def send(msg: Message, receiver: Subscriber): Unit
}
