package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity.Player

class PunchState extends State[Player] {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getPunchSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new IdleState)
    }
  }

  override def exit(c: Player): Unit = {
  }
}
