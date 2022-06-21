package mailbox

import akka.actor.ActorSystem
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import com.typesafe.config.Config
import subscribe.{AddMessage, Notify}

class PrioritizedMailbox(settings: ActorSystem.Settings, config: Config) extends UnboundedStablePriorityMailbox(PriorityGenerator{
  case action: AddMessage => {
    println("MAILBOX MESSAGE")
    action.msg.priority
  }
  case _: Notify.type => {
    println("MAILBOX NOTIFY")
    100
  }
  case test => {
    println(s"MAILBOX DEFAULT: ${test}")
    1000
  }
})
