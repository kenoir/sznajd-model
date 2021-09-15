import scala.util.Random

object UnitedWeStandApp extends App {
  import Debatables._

  def runCycles(cycleCount: Int = 100000): Society[Int] = {
    val society = Society.create() { () =>
      Random.nextInt()
    }

    (1 to cycleCount).foldLeft(society) { (society, _) =>
      printSociety(society)
      society.update
    }
  }

  def printSociety(society: Society[Int]): Unit = {
    society.participants.foreach { p =>
      print(f"${p.opinion} ")
    }
    print("\n")
  }

  printSociety(
    runCycles()
  )
}
