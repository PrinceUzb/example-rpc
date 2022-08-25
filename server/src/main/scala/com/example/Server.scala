package com.example

import cats.effect._
import higherkindness.mu.rpc.server._
import proto.Greeter

object Server extends IOApp {
  implicit val greeter: Greeter[IO] = new HappyGreeter[IO]

  def run(args: List[String]): IO[ExitCode] =
    /*
     * From version 0.27.0, `proto.Greeter.bindService` returns a `Resource[F, ServerServiceDefinition]`
     * instead of an `F[ServerServiceDefinition]`, so in previous versions this block would look like:
     *
     * for {
     *   serviceDef <- proto.Greeter.bindService[IO]
     *   server <- GrpcServer.default[IO](12345, List(AddService(serviceDef)))
     *   _ <- GrpcServer.server[IO](server)
     * } yield ExitCode.Success
     */
    Greeter.bindService[IO].use { serviceDef =>
      for {
        server <- GrpcServer.default[IO](12345, List(AddService(serviceDef)))
        _ <- GrpcServer.server[IO](server)
      } yield ExitCode.Success
    }
}
