package Subscriber

final case class SubscriberSendException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)
