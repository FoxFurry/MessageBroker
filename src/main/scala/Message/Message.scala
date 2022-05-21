package Message

case class Message(
  var topic:String,
  var data:String
)

trait MessageSerializer {
  def serialize(msg: Message): Array[Byte]
  def deserialize(msgBytes: Array[Byte]): Message
}
