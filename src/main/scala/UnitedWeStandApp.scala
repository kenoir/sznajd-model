import scala.util.Random
import JsonFormats._ // Import your encoders
import io.circe.syntax._ // For .asJson

object UnitedWeStandApp extends App {
  import Debatable._

  def runCycles(cycleCount: Int = 20): Society[Boolean] = {
    val society = Society.create(population = 7)(() => Random.nextBoolean())
    (1 to cycleCount).foldLeft(society)((accSociety, _) => accSociety.update)
  }

  val finalSociety = runCycles()
  println(finalSociety.asJson.noSpaces) // Output JSON to stdout
}
