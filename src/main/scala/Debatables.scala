import scala.util.Random

object Debatables {
  implicit object IntDebatable extends Debatable[Int] {
    override def agrees(firstOpinion: Int, secondOpinion: Int): Boolean = firstOpinion > secondOpinion
    override def persuade(persuaderOpinions: (Int, Int), persuadedOpinion: Int): Int = {
      val (firstOpinion, secondOpinion) = persuaderOpinions

      (firstOpinion + secondOpinion) / 2
    }
    override def dissuade(dissuaderOpinions: (Int, Int), dissuadedOpinion: Int): Int = {
      val (firstOpinion, secondOpinion) = dissuaderOpinions

      val averageOpinion = (firstOpinion + secondOpinion) / 2

      if(Random.nextBoolean()) {
        dissuadedOpinion + averageOpinion
      } else {
        dissuadedOpinion - averageOpinion
      }
    }
  }
}