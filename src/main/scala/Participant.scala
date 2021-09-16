import java.util.UUID

case class Participant[T](uuid: UUID = UUID.randomUUID(), opinion: T) {
  def agreesWith(anotherOpinion: T)(implicit agreeable: Debatable[T]): Boolean = {
    agreeable.agrees(opinion, anotherOpinion)
  }

  def persuade(participant: Participant[T])(implicit agreeable: Debatable[T]): Participant[T] =
    participant.copy(opinion =
      agreeable.persuade(participant.opinion, opinion)
    )

  def dissuade(participant: Participant[T])(implicit agreeable: Debatable[T]): Participant[T] =
    participant.copy(opinion =
      agreeable.dissuade(participant.opinion, opinion)
    )
}
