package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player

class HitState(damage: Int) extends PlayerState {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getHitSpritesheet)

    c.applyDamage(damage)

    c.tryPlaySoundOnCurrentFrame()
  }

  override def update(c: Player): Unit = {
    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new IdleState)
    }
  }

  override def exit(c: Player): Unit = {}
}
