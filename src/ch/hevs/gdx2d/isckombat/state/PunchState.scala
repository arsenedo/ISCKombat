package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig}

import scala.collection.immutable.HashMap

class PunchState extends State {
  override def enter(c: entity.Entity): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getPunchSpritesheet)
  }

  override def update(c: entity.Entity): Unit = {
    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new IdleState)
    }
  }

  override def exit(c: entity.Entity): Unit = {
  }

  override def handleKeyDown(keycode: Int, c: entity.Entity): Unit = {

  }

  override def handleKeyUp(keycode: Int, c: entity.Entity): Unit = {

  }
}
