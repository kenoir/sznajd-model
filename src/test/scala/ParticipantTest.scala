import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ParticipantTest extends AnyFunSpec with Matchers {
  import Debatable._

  describe("Participant") {
    val testParticipant = Participant[Boolean](opinion = Random.nextBoolean())
    val opposingParticipant =
      Participant[Boolean](opinion = !testParticipant.opinion)

    describe("agreesWith") {
      it("with an agreeing opinion should return true") {
        testParticipant.agreesWith(testParticipant.opinion) shouldBe true
      }

      it("with a disagreeing opinion should return true") {
        testParticipant.agreesWith(!testParticipant.opinion) shouldBe false
      }
    }

    describe("dissuade") {
      it("should produce an opposing opinion to the provided participant") {
        testParticipant
          .dissuade(opposingParticipant)
          .opinion shouldBe !opposingParticipant.opinion
      }
    }

    describe("persuade") {
      it("should produce the same opinion as the provided participant") {
        testParticipant
          .persuade(opposingParticipant)
          .opinion shouldBe !testParticipant.opinion
      }
    }
  }
}
