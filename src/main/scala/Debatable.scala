import scala.util.Random

trait Debatable[T] {
  def agrees(firstOpinion: T, secondOpinion: T): Boolean
  def persuade(persuaderOpinion: T, persuadedOpinion: T): T
  def dissuade(dissuaderOpinion: T, dissuadedOpinion: T): T
}