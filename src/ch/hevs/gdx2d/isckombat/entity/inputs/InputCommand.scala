package ch.hevs.gdx2d.isckombat.entity.inputs

import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction

case class InputCommand(action: InputAction) {
  var hasActivatedACombo: Boolean = false

  private val activityTimeMs: Double = 300.0
  private val createdAt: Long = System.nanoTime()

  def isActive: Boolean = {
    nanoToMs(System.nanoTime() - createdAt) <= activityTimeMs
  }

  def getCreatedAt: Long = createdAt

  /**
   * Compares whether distance with another time is lower than activity time.
   * (useful for combos)
   * @param otherTimeNs - time to compare with in nanoseconds
   * @return
   */
  def compare(otherTimeNs: Long): Boolean = {
    math.abs(nanoToMs(otherTimeNs - createdAt)) <= activityTimeMs
  }

  private def nanoToMs(nanoTime: Long): Double = {
    nanoTime / math.pow(10, 6)
  }
}