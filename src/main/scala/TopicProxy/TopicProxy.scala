package TopicProxy

import Message.Message
import Subscriber.{AddMessage, AddSub, Notify, Subscriber, TopicAction, TopicManager}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, ActorSystem}

object TopicProxy {
  val topics: scala.collection.mutable.Map[String, ActorRef[TopicAction]] = scala.collection.mutable.Map[String, ActorRef[TopicAction]]()

  def apply(): Behavior[ProxyAction] = {
    Behaviors.setup {
      context =>
        Behaviors.receive{ (_, message) =>
          message match {
            case NewSub(sub: Subscriber) =>
              val targetActor = getActorForTopic(context, sub.topic)

              context.log.info(s"New subscriber to ${sub.topic} manager")

              targetActor ! AddSub(sub)                                    // Add subscriber to this topic

              Behaviors.same

            case NewMessage(msg: Message) =>
              val targetActor = getActorForTopic(context, msg.topic)

              context.log.info(s"Sending message to ${msg.topic} manager")

              targetActor ! AddMessage(msg) // Add message to the new topic
              targetActor ! Notify          // Instantly notify everyone from this topic

              Behaviors.same

            case NotifyAll =>
              context.log.info(s"Notifying everyone")
              topics.foreach(tuple => tuple._2 ! Notify)  // Send notify to each topic

              Behaviors.same

          }
        }
    }
  }

  def getActorForTopic(context: ActorContext[ProxyAction], topic: String): ActorRef[TopicAction] = {
    if (!topics.isDefinedAt(topic)) { // If manager is not defined for this topic
      context.log.info(s"Created ${topic} manager")

      topics(topic) = context.spawn(TopicManager(), topic) // Create a new manager for this topic
    }

    topics(topic)
  }
}
