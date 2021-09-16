import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ParticipantGroupTest extends AnyFunSpec with Matchers {
  import Debatable._

  describe("ParticipantGroup") {
    describe("discuss") {
      describe("where an innerPair agree") {
        val agreeingOpinion = Random.nextBoolean()
        val agreeableParticipant1 = Participant(opinion = agreeingOpinion)
        val agreeableParticipant2 = Participant(opinion = agreeingOpinion)

        val agreeingPair =
          ParticipantPair(agreeableParticipant1, agreeableParticipant2)

        it("should persuade farLeft and farRight neighbours") {
          val farLeftParticipant = Participant(opinion = Random.nextBoolean())
          val farRightParticipant = Participant(opinion = Random.nextBoolean())

          val participantGroup = ParticipantGroup[Boolean](
            farLeft = farLeftParticipant,
            innerPair = agreeingPair,
            farRight = farRightParticipant
          )

          val updatedGroup = participantGroup.discuss

          updatedGroup.farLeft.opinion shouldBe agreeingOpinion
          updatedGroup.farRight.opinion shouldBe agreeingOpinion
        }
      }

      describe("where an innerPair disagree") {
        val contentiousOpinion = Random.nextBoolean()
        val disagreeableParticipant1 = Participant(opinion = contentiousOpinion)
        val disagreeableParticipant2 =
          Participant(opinion = !contentiousOpinion)

        val disagreeingPair =
          ParticipantPair(disagreeableParticipant1, disagreeableParticipant2)

        it("should dissuade farLeft and farRight neighbours") {

          val farLeftParticipant = Participant(opinion = Random.nextBoolean())
          val farRightParticipant = Participant(opinion = Random.nextBoolean())

          val participantGroup = ParticipantGroup[Boolean](
            farLeft = farLeftParticipant,
            innerPair = disagreeingPair,
            farRight = farRightParticipant
          )

          val updatedGroup = participantGroup.discuss

          updatedGroup.farLeft.opinion shouldBe !disagreeingPair.left.opinion
          updatedGroup.farRight.opinion shouldBe !disagreeingPair.right.opinion
        }
      }
    }

    it(
      "should create a list of participants ordered from farLeft, left, right, farRight"
    ) {
      val participantGroup = ParticipantGroup[Boolean](
        farLeft = Participant(opinion = Random.nextBoolean()),
        innerPair = ParticipantPair(
          left = Participant(opinion = Random.nextBoolean()),
          right = Participant(opinion = Random.nextBoolean())
        ),
        farRight = Participant(opinion = Random.nextBoolean())
      )

      val participantGroupList = participantGroup.toList
      participantGroupList shouldBe a[List[_]]
      participantGroupList.size shouldBe 4

      participantGroupList(0) shouldBe participantGroup.farLeft
      participantGroupList(1) shouldBe participantGroup.innerPair.left
      participantGroupList(2) shouldBe participantGroup.innerPair.right
      participantGroupList(3) shouldBe participantGroup.farRight
    }
  }
}
