package ch.hevs.gdx2d.isckombat.state.playerStates.Crouch

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.state.playerStates.PlayerState

class CrouchKickState() extends PlayerState {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getCrouchkickSpritesheet)

    c.tryPlaySoundOnCurrentFrame()
  }

  override def update(c: Player): Unit = {
    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new CrouchState)
    }

  }

  override def exit(c: Player): Unit = {
  }

}
