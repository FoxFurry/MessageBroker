package Message

trait MessageSerializer {
  def serialize(msg: Message): Array[Byte]
  def deserialize(msgBytes: Array[Byte]): Message
}
