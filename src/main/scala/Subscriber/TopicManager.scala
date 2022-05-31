package Subscriber

import Message.Message

import scala.collection.mutable
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

class TopicManager(topicValue: String) {
  sealed trait Action

  case class AddSub(sub: Subscriber) extends Action
  case class AddMessage(msg: Message) extends Action
  case object Notify extends Action

  val topicSubscribers: Array[Subscriber] = Array()
  val messageQueue: mutable.Queue[Message] = mutable.Queue()

  def apply(): Behavior[Action] =
    Behaviors.receive{ (context, message) =>
      message match {
        case AddSub(sub: Subscriber) => {
          topicSubscribers :+ sub
          Behaviors.same
        }
        case AddMessage(msg: Message) => {
          messageQueue.enqueue(msg)
          Behaviors.same
        }
        case Notify => {
          // TODO Mechanism which will send messages to all subs
          Behaviors.same
        }
      }
    }
}
