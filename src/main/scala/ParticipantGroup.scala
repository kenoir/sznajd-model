case class ParticipantGroup[T](
  farLeft: Participant[T],
  innerPair: ParticipantPair[T],
  farRight: Participant[T]
) {

  def discuss(implicit agreeable: Debatable[T]): ParticipantGroup[T] =
    if (innerPair.agrees) {
      this.copy(
        farLeft = farLeft.persuade(innerPair.left),
        farRight = farRight.persuade(innerPair.right)
      )
    } else {
      this.copy(
        farLeft = farLeft.dissuade(innerPair.left),
        farRight = farRight.dissuade(innerPair.right)
      )
    }

  def toList =
    List(farLeft, innerPair.left, innerPair.right, farRight)
}
