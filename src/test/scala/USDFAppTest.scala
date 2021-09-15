
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalacheck.derive.MkArbitrary

import scala.util.Random


class UnitedWeStandAppTest extends AnyFunSpec with Matchers {
  describe("Society") {
    describe("generateParticipants") {
      it("generates a list of Participants") {
        val participantListSize = 10
        val society = Society.create[Int](participantListSize) { () =>
          Random.nextInt()
        }

        society.participants.size shouldBe participantListSize
      }
    }
    describe("getGroup") {
      it("gets a group") {
        val listSize = 10
        val arbitaryParticipant = MkArbitrary[Participant[Int]].arbitrary
        val arbitaryParticipantList = (1 to listSize).map(_ => arbitaryParticipant.arbitrary.sample).toList.flatten

        val participantsWithIndex = arbitaryParticipantList.zipWithIndex.toMap

        (1 to 100).foreach { _ =>
          val group = Society.getGroup(arbitaryParticipantList)

          val firstIndex = participantsWithIndex(group.left)
          val secondIndex = participantsWithIndex(group.innerPair.first)
          val thirdIndex = participantsWithIndex(group.innerPair.second)
          val fourthIndex = participantsWithIndex(group.right)

          secondIndex shouldBe (firstIndex + 1) % (listSize - 1)
          thirdIndex shouldBe (secondIndex + 1) % (listSize - 1)
          fourthIndex shouldBe (thirdIndex + 1) % (listSize - 1)
        }
      }
    }
  }
}
