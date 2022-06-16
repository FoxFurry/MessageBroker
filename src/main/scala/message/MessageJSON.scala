package message

import play.api.libs.json.{Json, OFormat}

class MessageJSON extends MessageSerializer {
  implicit val MessageFormat: OFormat[Message] = Json.format[Message]

  override def serialize(msg: Message): Array[Byte] = {
    Json.stringify(Json.toJson(msg)).getBytes()
  }

  override def deserialize(msgBytes: Array[Byte]): Message = {
    Json.parse(msgBytes).as[Message]
  }
}
