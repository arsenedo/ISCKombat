package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import com.badlogic.gdx.math.Vector2

class UncontrolledWalkState(walkToX: Float, acceptedOffset: Float = 300f, dizzyAfterwardsDuration: Float = 0f) extends PlayerState {
  private val velocityX: Float = 10f

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(owner.getSpritesLoader.getWalkSpritesheet)
  }

  override def update(owner: Player): Unit = {
    val flipAdjustedPos: Vector2 = owner.getFlipAdjustedPosition
    if (flipAdjustedPos.x > walkToX + acceptedOffset || flipAdjustedPos.x < walkToX - acceptedOffset) {
      owner.position.add(math.signum(walkToX - flipAdjustedPos.x) * velocityX, 0)
      return
    }

    if (dizzyAfterwardsDuration > 0) {
      owner.updateState(new DizzyState(dizzyAfterwardsDuration))
    } else {
      owner.updateState(new IdleState)
    }
  }

  override def exit(owner: Player): Unit = {}
}
