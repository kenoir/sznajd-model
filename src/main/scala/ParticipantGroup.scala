case class ParticipantGroup[T](left: Participant[T], innerPair: ParticipantPair[T], right: Participant[T]) {
  def discuss(implicit agreeable: Debatable[T]): ParticipantGroup[T] = {
    val newLeft = innerPair.discuss(left)
    val newRight = innerPair.discuss(right)

    this.copy(left=newLeft, right=newRight)
  }

  def toList =
    List(left, innerPair.first, innerPair.second, right)
}