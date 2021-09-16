import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalacheck.derive.MkArbitrary

import scala.util.Random

class SocietyTest extends AnyFunSpec with Matchers {
  describe("Society") {
    describe("generateParticipants") {
      it("generates a list of Participants") {
        val participantListSize = 10
        val society = Society.create[Boolean](participantListSize) { () =>
          Random.nextBoolean()
        }

        society.participants.size shouldBe participantListSize
      }
    }

    describe("getGroup") {
      it("gets a group") {
        val listSize = 10
        val arbitaryParticipant = MkArbitrary[Participant[Int]].arbitrary
        val arbitaryParticipantList = (1 to listSize)
          .map(_ => arbitaryParticipant.arbitrary.sample)
          .toList
          .flatten

        val participantsWithIndex = arbitaryParticipantList.zipWithIndex.toMap

        (1 to 100).foreach { _ =>
          val group = Society.getGroup(arbitaryParticipantList)

          val firstIndex = participantsWithIndex(group.farLeft)
          val secondIndex = participantsWithIndex(group.innerPair.left)
          val thirdIndex = participantsWithIndex(group.innerPair.right)
          val fourthIndex = participantsWithIndex(group.farRight)

          secondIndex shouldBe (firstIndex + 1) % (listSize - 1)
          thirdIndex shouldBe (secondIndex + 1) % (listSize - 1)
          fourthIndex shouldBe (thirdIndex + 1) % (listSize - 1)
        }
      }
    }
  }
}
