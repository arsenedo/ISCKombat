package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity
import ch.hevs.gdx2d.lib.GdxGraphics

trait State[T] {
  def enter(owner: T): Unit
  def update(owner: T): Unit
  def exit(owner: T): Unit
  def drawSprite(g: GdxGraphics, c: entity.Entity): Unit = {
    val flipAdjustedPos = c.getFlipAdjustedPosition
    g.draw(c.getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }
}
