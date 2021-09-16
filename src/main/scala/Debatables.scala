object Debatables {
  implicit object BoolDebatable extends Debatable[Boolean] {
    override def agrees(firstOpinion: Boolean, secondOpinion: Boolean): Boolean =
      firstOpinion == secondOpinion
    override def persuade(persuaderOpinion: Boolean, persuadedOpinion: Boolean): Boolean =
      persuaderOpinion
    override def dissuade(dissuaderOpinion: Boolean, dissuadedOpinion: Boolean): Boolean =
      !dissuaderOpinion
  }

  implicit object IntDebatable extends Debatable[Int] {
    override def agrees(firstOpinion: Int, secondOpinion: Int): Boolean = firstOpinion > secondOpinion
    override def persuade(persuaderOpinion: Int, persuadedOpinion: Int): Int =
      persuaderOpinion
    override def dissuade(dissuaderOpinion: Int, dissuadedOpinion: Int): Int = {
      dissuadedOpinion
    }
  }
}