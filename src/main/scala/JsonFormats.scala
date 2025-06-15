import io.circe.{Encoder, Json}
import io.circe.generic.semiauto._
import java.util.UUID

// Assuming Participant and Society are in the default package (as seen from ls output)

object JsonFormats {
  // Encoder for UUID
  implicit val uuidEncoder: Encoder[UUID] = Encoder.encodeString.contramap[UUID](_.toString)

  // Encoder for Participant[Boolean]
  implicit val participantEncoder: Encoder[Participant[Boolean]] = deriveEncoder[Participant[Boolean]]

  // Encoder for Society[Boolean]
  implicit val societyEncoder: Encoder[Society[Boolean]] = deriveEncoder[Society[Boolean]]
}
