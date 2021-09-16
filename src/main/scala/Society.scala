import java.util.UUID

import grizzled.slf4j.Logging

import scala.collection.immutable.ListMap
import scala.util.Random

case class Society[T](participants: List[Participant[T]]) extends Logging {
  def update(implicit debatable: Debatable[T]): Society[T] = {
    val participantGroup = Society.getGroup(participants)
    val updatedparticipantGroup = participantGroup.discuss.toList

    val emptyListMap = new ListMap[UUID, Participant[T]]
    val participantMap: ListMap[UUID, Participant[T]] =
      participants.foldLeft(emptyListMap) { (listMap, participant) =>
        listMap.updated(participant.uuid, participant)
      }

    val updatedSocietyParticipants = updatedparticipantGroup.foldLeft(participantMap) { (pMap, participant) =>
      pMap.updated(participant.uuid, participant)
    }.values.toList

    info(updatedSocietyParticipants.map(_.opinion))
    copy(participants = updatedSocietyParticipants)
  }
}

object Society {
  def create[T](population: Int = 10)(opinionGenerator: () => T): Society[T] = {
    val participants = (1 to population).map { _ =>
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
      farLeft = participants(first),
      innerPair = ParticipantPair(
        participants(second),
        participants(third)
      ),
      farRight = participants(fourth)
    )
  }
}