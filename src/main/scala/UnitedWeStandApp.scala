import scala.util.Random

object UnitedWeStandApp extends App {
  import Debatables._

  def runCycles(cycleCount: Int = 20): Society[Boolean] = {
    val society = Society.create(population = 7) { () =>
      Random.nextBoolean()
    }

    (1 to cycleCount).foldLeft(society) { (society, _) =>
      society.update
    }
  }

  runCycles()
}
