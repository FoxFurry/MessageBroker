import Server.ServerHttp
import TopicProxy.{ProxyAction, TopicProxy, NotifyAll}
import akka.actor.typed.ActorSystem

import scala.concurrent.duration.{Duration, DurationInt}

object Main {
  def main(args: Array[String]): Unit = {
    val proxyGuardian: ActorSystem[ProxyAction] = ActorSystem(TopicProxy(), "proxy")

    import proxyGuardian.executionContext
    proxyGuardian.scheduler.scheduleAtFixedRate(Duration.Zero, 10.seconds) {
      new Runnable() { def run(): Unit = {
        proxyGuardian ! NotifyAll
      } }
    }
    val httpServer = new ServerHttp()(proxyGuardian)
    httpServer.Start()
  }
}