import java.util.UUID

case class Participant[T](uuid: UUID = UUID.randomUUID(), opinion: T) {
  def agreesWith(anotherOpinion: T)(implicit agreeable: Debatable[T]): Boolean =
    agreeable.agrees(opinion, anotherOpinion)
}
