package proto

import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.semiauto.deriveEncoder

case class HelloRequest(name: String)
object HelloRequest {
  implicit def encoder: Encoder.AsObject[HelloRequest] = deriveEncoder
  implicit def decoder: Decoder[HelloRequest] = deriveDecoder
}
