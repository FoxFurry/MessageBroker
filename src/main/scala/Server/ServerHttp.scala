package Server

import Api.ApiHttp
import Message.Message
import TopicProxy.{NewMessage, NewSub, ProxyAction}
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import Subscriber.Subscriber

import scala.concurrent.{ExecutionContextExecutor, Future}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val SubscribeFormat: RootJsonFormat[SubscribeModel] = jsonFormat2(SubscribeModel)
  implicit val MessageFormat: RootJsonFormat[MessageModel] = jsonFormat2(MessageModel)
}

class ServerHttp()(implicit system: ActorSystem[ProxyAction]) extends Server with Directives with JsonSupport {
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  var serverInstance: Future[Http.ServerBinding] = null
  def Start(): Unit = {

    val route = {
      path("subscribe") {
        post {
          entity(as[SubscribeModel]) { sub =>
            println(s"Received new subscriber for ${sub.topic}")

            system ! NewSub(new Subscriber( _address = sub.address,
                                            _topic = sub.topic,
                                             _api = new ApiHttp))

            complete(s"Received new subscriber for topic ${sub.topic}")
          }
        }
      }

      path("message") {
        post {
          entity(as[MessageModel]) { msg =>
            println(s"Received new message for ${msg.topic}")

            system ! NewMessage(new Message(topic = msg.topic, data = msg.data))

            complete(s"Received new message for topic ${msg.topic}")
          }
        }
      }
    }

    serverInstance = Http().newServerAt("localhost", 8080).bind(route)
  }

  def Stop(): Unit = {
    serverInstance.flatMap(_.unbind())
  }
}
