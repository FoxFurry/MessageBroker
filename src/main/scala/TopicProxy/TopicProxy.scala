package ServerManager

import Subscriber.{AddSub, Subscriber, TopicAction, TopicManager}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.stream.StreamRefMessages.ActorRef

object ServerManager {
  var system: ActorSystem[TopicAction] = ActorSystem(TopicManager(), "Topics")
  var topics: Map[String, ActorRef] = Map()
  def apply(): Behavior[ServerAction] =
    Behaviors.receive{ (_, message) =>
      message match {
        case NewSub(sub: Subscriber) =>
          if (topics.isDefinedAt(sub.topic)) {
            system ! AddSub(sub)
          } else {
          }
      }
    }
}
