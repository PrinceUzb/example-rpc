package proto

import higherkindness.mu.rpc.protocol.Custom
import higherkindness.mu.rpc.protocol.service

@service(Custom)
trait Greeter[F[_]] {
  def sayHello(req: HelloRequest): F[HelloResponse]
}

object Greeter {}
