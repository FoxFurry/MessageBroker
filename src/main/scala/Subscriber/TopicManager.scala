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

    private var topicSubscribers: Array[Subscriber] = Array()
    private val topicQueue: mutable.Queue[Message] = mutable.Queue()

    override def onMessage(message: TopicAction): Behavior[TopicAction] = {
      message match {

        case AddSub(sub: Subscriber) =>
          println(s"[$topic] Received new sub: addr: ${sub.address} topic: ${sub.topic}")
          topicSubscribers = topicSubscribers :+ sub  // Add subscriber to subscriber array

          this

        case AddMessage(msg: Message) =>
          println(s"[$topic] Received new message: data ${msg.data} topic: ${msg.topic}")
          topicQueue.enqueue(msg)                   // Add message to the queue

          this

        case Notify =>
          if (topicQueue.nonEmpty && !topicSubscribers.isEmpty) {            // If we have at least one subscriber
            val msg = topicQueue.dequeue()          // Pop the first message

            topicSubscribers.foreach(sub => sub.send(msg) match { // Send to each subscriber
              case Success(_) => println(s"[$topic] Successfully sent to ${sub.address}")
              case Failure(e) => println(s"[$topic] Failed sending to ${sub.address}. Reason: ${e.toString}")
            })
          }

          this
      }
    }
  }
}
