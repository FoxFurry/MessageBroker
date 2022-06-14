package TopicProxy

import Message.Message
import Subscriber.{AddMessage, AddSub, Notify, Subscriber, TopicAction, TopicManager}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem}

object TopicProxy {
  var system: ActorSystem[TopicAction] = ActorSystem(TopicManager(), "Topics")
  val topics: scala.collection.mutable.Map[String, ActorRef[TopicAction]] = scala.collection.mutable.Map[String, ActorRef[TopicAction]]()

  def apply(): Behavior[ProxyAction] =
    Behaviors.receive{ (_, message) =>
      message match {
        case NewSub(sub: Subscriber) =>
          if (topics.isDefinedAt(sub.topic)) {                                   // If manager is defined for this topic
            println(s"New subscriber to ${sub.topic} manager")
            topics(sub.topic) ! AddSub(sub)                                    // Add subscriber to this topic
          } else {                                                            // Else if manager is not defined for this topic
            val newManager = system.systemActorOf(TopicManager(), sub.topic) // Create a new manager for this topic
            println(s"Created ${sub.topic} manager and new subscriber")
            topics(sub.topic) = newManager
            newManager ! AddSub(sub)                                        // Add subscriber to the new topic
          }

          Behaviors.same

        case NewMessage(msg: Message) =>
          if (topics.isDefinedAt(msg.topic)) {                                   // If manager is defined for this topic
            println(s"Sending message to ${msg.topic} manager")
            topics(msg.topic) ! AddMessage(msg)                                // Add message to this topic
          } else {                                                            // Else if manager is not defined for this topic
            val newManager = system.systemActorOf(TopicManager(), msg.topic) // Create a new manager for this topic
            println(s"Created ${msg.topic} manager and sending message")
            topics(msg.topic) = newManager
            newManager ! AddMessage(msg)                                    // Add message to the new topic
          }

          Behaviors.same

        case NotifyAll =>
          println(s"Notifying everyone")
          topics.foreach(tuple => tuple._2 ! Notify)  // Send notify to each topic

          Behaviors.same
      }
    }
}
