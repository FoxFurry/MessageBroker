package proxy

import message.Message
import subscribe.Subscriber

sealed trait ProxyAction

case class NewMessage(msg: Message) extends ProxyAction
case class NewSub(sub: Subscriber) extends ProxyAction
object NotifyAll extends ProxyAction
