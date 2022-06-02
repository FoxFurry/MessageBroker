package API

final case class APIException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)
