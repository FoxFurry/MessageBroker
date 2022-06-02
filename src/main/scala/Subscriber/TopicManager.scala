package Subscriber

import Message.Message
import scala.util.{Success,Failure}
import scala.collection.mutable
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object TopicManager {
  sealed trait Action

  case class AddSub(sub: Subscriber) extends Action
  case class AddMessage(msg: Message) extends Action
  case object Notify extends Action

  var topicSubscribers: Array[Subscriber] = Array()
  val topicMessage: mutable.Queue[Message] = mutable.Queue()

  def apply(): Behavior[Action] =
    Behaviors.receive{ (_, message) =>
      message match {

        case AddSub(sub: Subscriber) =>
          println(s"Received new sub ${sub.address}")
          topicSubscribers = topicSubscribers :+ sub  // Add subscriber to subscriber array

          Behaviors.same

        case AddMessage(msg: Message) =>
          println(s"Received new message ${msg.data}")
          topicMessage.enqueue(msg)                   // Add message to the queue

          Behaviors.same

        case Notify =>
          if (!topicSubscribers.isEmpty) {            // If we have at least one subscriber
            val msg = topicMessage.dequeue()          // Pop the first message

            topicSubscribers.foreach(sub => sub.send(msg) match { // Send to each subscriber
              case Success(_) => println(s"Successfully sent to ${sub.address}")
              case Failure(e) => println(s"Failed sending to ${sub.address}. Reason: ${e.toString}")
            })
          }

          Behaviors.same
      }
    }
}
