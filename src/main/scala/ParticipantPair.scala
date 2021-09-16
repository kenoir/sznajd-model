case class ParticipantPair[T](left: Participant[T], right: Participant[T]) {
  def agrees(implicit agreeable: Debatable[T]): Boolean = {
    left.agreesWith(right.opinion)
  }
}