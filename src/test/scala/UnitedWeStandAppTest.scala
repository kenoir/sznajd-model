import org.scalatest.funsuite.AnyFunSuite
import io.circe.parser.parse
import io.circe.syntax._ // For .asJson
import io.circe.Json // For explicit Json type usage
import JsonFormats._ // Your encoders

// Assuming UnitedWeStandApp is in the default package
// Assuming Participant and Society case classes are in the default package

class UnitedWeStandAppTest extends AnyFunSuite {

  test("UnitedWeStandApp.runCycles output should be valid JSON and contain non-empty participants array") {
    // Directly call runCycles and serialize, to avoid capturing stdout
    val society = UnitedWeStandApp.runCycles(cycleCount = 5) // Use a small cycle count for testing
    val jsonString = society.asJson.noSpaces

    // 1. Check if the string is valid JSON
    val parsedJsonResult = parse(jsonString)
    assert(parsedJsonResult.isRight, s"Output should be valid JSON. Parsing error: ${parsedJsonResult.left.getOrElse("")}")

    // 2. Perform checks on the parsed JSON structure
    val json = parsedJsonResult.getOrElse(throw new RuntimeException("JSON parsing failed in test, this should not happen due to the assert above."))

    // Check for the presence of the 'participants' array
    val participantsCursor = json.hcursor.downField("participants")
    assert(participantsCursor.succeeded, "JSON should contain a 'participants' field.")

    // Check if 'participants' is an array and is non-empty
    val participantsArray = participantsCursor.as[List[Json]]
    assert(participantsArray.isRight, "'participants' field should be an array.")
    assert(participantsArray.getOrElse(Nil).nonEmpty, "Participants array should not be empty.")

    // 3. Optionally, check structure of a participant (if needed, more detailed)
    val firstParticipantOpt = participantsArray.getOrElse(Nil).headOption
    assert(firstParticipantOpt.isDefined, "Participant array should have at least one participant.")

    val firstParticipantJson = firstParticipantOpt.get
    assert(firstParticipantJson.hcursor.downField("uuid").as[String].isRight, "Participant should have a 'uuid' string field.")
    assert(firstParticipantJson.hcursor.downField("opinion").as[Boolean].isRight, "Participant should have an 'opinion' boolean field.")
  }
}
