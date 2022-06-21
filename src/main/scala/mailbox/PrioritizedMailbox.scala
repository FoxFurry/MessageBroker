package mailbox

import akka.actor.{ActorSystem, Kill, PoisonPill}
import akka.dispatch.PriorityGenerator
import akka.dispatch.UnboundedStablePriorityMailbox
import message.Message

object PrioritizedMailbox {
  private class PrioritizedGenerator private(val poisonPillPriority: Int, val killPriority: Int) extends PriorityGenerator {
    override def gen(message: Any): Int = {
      message match {
        case Message(topic:String,data:String,priority:Int
      ) => priority
        case PoisonPill =>
          poisonPillPriority
        case kill =>
          killPriority
        

      }
    }
  }
}

class PrioritizedMailbox(val settings: ActorSystem.Settings, val config: Nothing) extends UnboundedStablePriorityMailbox(new PrioritizedMailbox.PrioritizedGenerator(if (config.hasPath("priority.poison-pill")) config.getInt("priority.poison-pill")
else PrioritizedMessage.HIGH, if (config.hasPath("priority.kill-pill")) config.getInt("priority.kill-pill")
else PrioritizedMessage.HIGH)) {
}
