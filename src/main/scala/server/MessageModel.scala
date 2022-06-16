package server

case class MessageModel(
  var topic:String,
  var data:String,
  var priority:String,
)