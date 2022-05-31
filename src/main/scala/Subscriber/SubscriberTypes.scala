package Subscriber

object SubscriberTypes extends Enumeration {
  type SubscriberType = Value

  val HTTP, XMPP, MQTT = Value
}
