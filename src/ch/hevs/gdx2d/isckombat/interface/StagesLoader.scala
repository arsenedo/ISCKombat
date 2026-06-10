package ch.hevs.gdx2d.isckombat.interface

import scala.util.Random

object StagesLoader {
  val stages: Array[String] = Array(
    "palace_gates",
    "throne_room",
    "warrior_shrine"
  )

  def createStage(name: String): Option[Stage] = {
    if (!stages.contains(name)) return None

    Some(Stage(name))
  }

  def createRandomStage: Stage = {
    createStage(stages(Random.between(0, stages.length))).get
  }
}
