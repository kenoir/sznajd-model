import java.util.UUID

import scala.collection.immutable.ListMap
import scala.util.Random

case class Participant[T](uuid: UUID = UUID.randomUUID(), opinion: T) extends Opinionated[T]
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
case class ParticipantGroup[T](left: Participant[T], innerPair: ParticipantPair[T], right: Participant[T]) {
  def discuss(implicit agreeable: Debatable[T]): ParticipantGroup[T] = {
    val newLeft = innerPair.discuss(left)
    val newRight = innerPair.discuss(right)

    this.copy(left=newLeft, right=newRight)
  }

  def toList =
    List(left, innerPair.first, innerPair.second, right)
}

trait Debatable[T] {
  def agrees(firstOpinion: T, secondOpinion: T): Boolean
  def persuade(persuaderOpinions: (T,T), persuadedOpinion: T): T
  def dissuade(dissuaderOpinions: (T,T), dissuadedOpinion: T): T
}

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

trait Opinionated[T] {
  val opinion: T

  def agreesWith(anotherOpinion: T)(implicit agreeable: Debatable[T]): Boolean =
    agreeable.agrees(opinion, anotherOpinion)
}


case class Society[T](participants: List[Participant[T]]) {
  def update(implicit debatable: Debatable[T]): Society[T] = {
    val participantGroup = Society.getGroup(participants)
    val updatedParticipantList = participantGroup.discuss.toList

    val emptyListMap = new ListMap[UUID, Participant[T]]
    val participantMap: ListMap[UUID, Participant[T]] =
      participants.foldLeft(emptyListMap) { (listMap, participant) =>
        listMap.updated(participant.uuid, participant)
      }

    val updatedSocietyParticipants = updatedParticipantList.foldLeft(participantMap) { (pMap, participant) =>
      pMap.updated(participant.uuid, participant)
    }.values.toList

    copy(participants = updatedSocietyParticipants)
  }
}

object Society {
  def create[T](listSize: Int = 10)(opinionGenerator: () => T): Society[T] = {
    val participants = (1 to listSize).map { _ =>
      Participant(opinion=opinionGenerator())
    }.toList

    new Society[T](participants)
  }

  def getGroup[T](participants: List[Participant[T]]): ParticipantGroup[T] = {
    assert(participants.size >= 4, "Participant group must be 4 or larger!")

    val first = Random.between(0, participants.size)
    val second = (first + 1) % (participants.size - 1)
    val third = (first + 2) % (participants.size - 1)
    val fourth = (first + 3) % (participants.size - 1)

    ParticipantGroup(
      left = participants(first),
      innerPair = ParticipantPair(
        participants(second),
        participants(third)
      ),
      right = participants(fourth)
    )
  }
}

object UnitedWeStandApp extends App {
  import Debatables._

  def runCycles(cycleCount: Int = 100000): Society[Int] = {
    val society = Society.create() { () =>
      Random.nextInt()
    }

    (1 to cycleCount).foldLeft(society) { (society, _) =>
      printSociety(society)
      society.update
    }
  }

  def printSociety(society: Society[Int]): Unit = {
    val opinions = society.participants.map(_.opinion)
    val avg = opinions.sum / opinions.size
//    print(avg)
    society.participants.foreach { p =>
      print(f"${p.opinion} ")
    }
    print("\n")
  }

  printSociety(
    runCycles()
  )
}
