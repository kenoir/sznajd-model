import java.util.UUID

import scala.collection.immutable.ListMap
import scala.util.Random

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