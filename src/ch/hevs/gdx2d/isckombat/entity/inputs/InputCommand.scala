package ch.hevs.gdx2d.isckombat.entity.inputs

import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction

case class InputCommand(action: InputAction) {
  private val activityTimeMs: Double = 300.0
  private val createdAt: Long = System.nanoTime()

  def isActive: Boolean = {
    nanoToMs(System.nanoTime() - createdAt) <= activityTimeMs
  }

  private def nanoToMs(nanoTime: Long): Double = {
    nanoTime / math.pow(10, 6)
  }
}