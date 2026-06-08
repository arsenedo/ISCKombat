package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player

class DizzyState(val duration: Float) extends PlayerState {
  val enterTimestamp: Long = System.nanoTime()

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(owner.getSpritesLoader.getHitSpritesheet)
  }

  override def update(owner: Player): Unit = {
    if (toSeconds(enterTimestamp) + duration <= toSeconds(System.nanoTime())) {
      owner.updateState(new IdleState)
    }
  }

  override def exit(owner: Player): Unit = {}

  private def toSeconds(nanoTime: Long): Long = {
    (nanoTime.toDouble / math.pow(10, 9)).toLong
  }
}
