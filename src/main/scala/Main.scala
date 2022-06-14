import Server.ServerHttp
import TopicProxy.{ProxyAction, TopicProxy}
import akka.actor.typed.ActorSystem

object Main {
  def main(args: Array[String]): Unit = {
    val rootGuardian: ActorSystem[ProxyAction] = ActorSystem(TopicProxy(), "proxy")

    val httpServer = new ServerHttp()(rootGuardian)
    httpServer.Start()
  }
}