import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ParticipantPairTest extends AnyFunSpec with Matchers {
  import Debatable._

  describe("ParticipantPair") {
    describe("when a participant pair agrees") {
      val agreeingOpinion = Random.nextBoolean()
      val agreeingPair = ParticipantPair(
        Participant(opinion = agreeingOpinion),
        Participant(opinion = agreeingOpinion)
      )

      it("should return true for agrees") {
        agreeingPair.agrees shouldBe true
      }
    }
    describe("when a participant pair disagrees") {
      val agreeingOpinion = Random.nextBoolean()
      val agreeingPair = ParticipantPair(
        Participant(opinion = agreeingOpinion),
        Participant(opinion = !agreeingOpinion)
      )

      it("should return false for agrees") {
        agreeingPair.agrees shouldBe false
      }
    }
  }
}
