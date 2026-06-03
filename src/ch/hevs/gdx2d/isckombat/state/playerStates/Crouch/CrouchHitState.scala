package ch.hevs.gdx2d.isckombat.state.playerStates.Crouch

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.state.playerStates.PlayerState

class CrouchHitState(damage: Int) extends PlayerState {

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(owner.getSpritesLoader.getCrouchHitSpritesheet)

    owner.applyDamage(damage)
  }

  override def update(owner: Player): Unit = {
    if (owner.getCurrentFrame == owner.getCurrentSpriteConfig.nFrames) {
      owner.updateState(new CrouchState)
    }
  }

  override def exit(owner: Player): Unit = {}
}
