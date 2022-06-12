package Subscriber

import Message.Message

sealed trait TopicAction

case class AddSub(sub: Subscriber) extends TopicAction
case class AddMessage(msg: Message) extends TopicAction
case object Notify extends TopicAction
