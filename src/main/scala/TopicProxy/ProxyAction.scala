package ServerManager

import Message.Message
import Subscriber.Subscriber

sealed trait ServerAction

case class NewMessage(msg: Message) extends ServerAction
case class NewSub(sub: Subscriber) extends ServerAction