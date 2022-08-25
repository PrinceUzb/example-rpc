package com.example

import cats.Applicative
import cats.syntax.applicative._
import proto.Greeter
import proto.HelloRequest
import proto.HelloResponse

class HappyGreeter[F[_]: Applicative] extends Greeter[F] {
  def sayHello(req: HelloRequest): F[HelloResponse] =
    HelloResponse(s"Hello, ${req.name}!", happy = true).pure[F]
}
