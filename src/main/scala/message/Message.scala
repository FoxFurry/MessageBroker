package message

case class Message(
  var topic:String,
  var data:String,
  var priority:Int
)
