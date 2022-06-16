package server

trait Server {
  def Start(): Unit
  def Stop(): Unit
}
