import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

import io.circe.Decoder
import io.circe.Encoder
import io.circe.parser
import io.grpc.MethodDescriptor

package object proto {
  implicit def marshaller[T: Encoder: Decoder]: MethodDescriptor.Marshaller[T] =
    new MethodDescriptor.Marshaller[T] {
      override def stream(value: T): InputStream = {
        val encodedValue = Encoder[T].apply(value)
        new ByteArrayInputStream(encodedValue.noSpaces.getBytes(StandardCharsets.UTF_8))
      }

      override def parse(stream: InputStream): T = {
        val encodedBytes = Array.ofDim[Byte](stream.available())
        stream.read(encodedBytes)

        val encodedValue = new String(encodedBytes, StandardCharsets.UTF_8)
        parser.parse(encodedValue).map(_.hcursor).flatMap(Decoder[T].apply(_)) match {
          case Left(error) => throw new Exception(error)
          case Right(value) => value
        }
      }
    }
}
