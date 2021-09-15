import scala.util.Random

trait Debatable[T] {
  def agrees(firstOpinion: T, secondOpinion: T): Boolean
  def persuade(persuaderOpinions: (T,T), persuadedOpinion: T): T
  def dissuade(dissuaderOpinions: (T,T), dissuadedOpinion: T): T
}