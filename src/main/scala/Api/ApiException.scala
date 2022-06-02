package Api

final case class ApiException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)
