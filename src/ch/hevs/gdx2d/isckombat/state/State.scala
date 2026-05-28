package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity
import ch.hevs.gdx2d.lib.GdxGraphics

trait State {
  def enter(c: entity.Entity): Unit
  def update(c: entity.Entity): Unit
  def exit(c: entity.Entity): Unit
  def handleKeyDown(keycode: Int, c: entity.Entity): Unit
  def handleKeyUp(keycode: Int, c: entity.Entity): Unit
  def drawSprite(g: GdxGraphics, c: entity.Entity): Unit = {
    val flipAdjustedPos = c.getFlipAdjustedPosition
    g.draw(c.getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }
}
