case class ParticipantPair[T](first: Participant[T], second: Participant[T]) {
  def agrees(implicit agreeable: Debatable[T]): Boolean = {
    first.agreesWith(second.opinion)
  }

  def discuss(participant: Participant[T])(implicit agreeable: Debatable[T]): Participant[T] = {
    val updatedOpinion = if(agrees) {
      agreeable.persuade((first.opinion, second.opinion), participant.opinion)
    } else {
      agreeable.dissuade((first.opinion, second.opinion), participant.opinion)
    }

    participant.copy(opinion = updatedOpinion)
  }
}