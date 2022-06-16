package Subscriber

import Message.Message

import scala.util.{Failure, Random, Success}
import scala.collection.mutable
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object TopicManager {
  def apply(): Behavior[TopicAction] =
    Behaviors.setup(context => new TopicManagerBehaviour(context))

  class TopicManagerBehaviour(context: ActorContext[TopicAction]) extends AbstractBehavior[TopicAction](context) {
    val topic: Int = Random.nextInt(1000)

    private var subscribers: Array[Subscriber] = Array()
    private var messages: Array[Message] = Array()

    override def onMessage(message: TopicAction): Behavior[TopicAction] = {
      message match {

        case AddSub(sub: Subscriber) =>
          context.log.info(s"[$topic] Received new sub: ${sub.address}, ${sub.topic}")
          subscribers = subscribers :+ sub  // Add subscriber to subscriber array

          this

        case AddMessage(msg: Message) =>
          context.log.info(s"[$topic] Received new message: data ${msg.data} topic: ${msg.topic}")
          messages = messages :+ msg                  // Add message to the queue

          this

        case Notify =>
          if (messages.nonEmpty && !subscribers.isEmpty) {            // If we have at least one subscriber

            context.log.info(s"[$topic] Sending messages to subscribers")

            subscribers.foreach(sub => {
              val currentCursor = sub.getCursor
              if (messages.length <= currentCursor ){
                context.log.error(s"[$topic] Subscriber has cursor at $currentCursor which exceeds message array ${messages.length}")
              } else {
                sub.send(messages(currentCursor))
              }
            })
          }

          this
      }
    }
  }
}
