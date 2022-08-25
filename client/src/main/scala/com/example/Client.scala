package com.example

import scala.Console.in
import scala.Console.out

import cats.effect._
import higherkindness.mu.rpc._
import proto.Greeter
import proto.HelloRequest

object Client extends IOApp {
  val channelFor: ChannelFor = ChannelForAddress("localhost", 12345)

  val serviceClient: Resource[IO, Greeter[IO]] = Greeter.client[IO](channelFor)

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(out.println("Please enter your name: "))
      name <- IO(in.readLine())
      response <- serviceClient.use(c => c.sayHello(HelloRequest(name)))
      serverMood = if (response.happy) "happy" else "unhappy"
      _ <- IO(out.println(s"The $serverMood server says '${response.greeting}'"))
    } yield ExitCode.Success
}
