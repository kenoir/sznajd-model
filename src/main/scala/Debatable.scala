trait Debatable[T] {
  def agrees(firstOpinion: T, secondOpinion: T): Boolean
  def persuade(persuaderOpinion: T, persuadedOpinion: T): T
  def dissuade(dissuaderOpinion: T, dissuadedOpinion: T): T
}

object Debatable {

  implicit object BoolDebatable extends Debatable[Boolean] {

    override def agrees(
      firstOpinion: Boolean,
      secondOpinion: Boolean
    ): Boolean =
      firstOpinion == secondOpinion

    override def persuade(
      persuaderOpinion: Boolean,
      persuadedOpinion: Boolean
    ): Boolean =
      persuaderOpinion

    override def dissuade(
      dissuaderOpinion: Boolean,
      dissuadedOpinion: Boolean
    ): Boolean =
      !dissuaderOpinion
  }
}
