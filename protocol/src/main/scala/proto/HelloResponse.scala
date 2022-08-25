package proto
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class HelloResponse(greeting: String, happy: Boolean)
object HelloResponse {
  implicit def encoder: Encoder.AsObject[HelloResponse] = deriveEncoder
  implicit def decoder: Decoder[HelloResponse] = deriveDecoder
}
