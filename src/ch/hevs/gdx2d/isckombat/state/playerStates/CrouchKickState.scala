package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player

class CrouchKickState() extends PlayerState {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getCrouchkickSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new CrouchState)
    }

  }

  override def exit(c: Player): Unit = {
  }

}
