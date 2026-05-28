package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity.{Entity, Player}
import ch.hevs.gdx2d.lib.GdxGraphics

class VictoryState extends State[Player] {

  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getVictorySpritesheet)
  }

  override def update(c: Player): Unit = {

  }

  override def exit(c: Player): Unit = {

  }

  override def drawSprite(g: GdxGraphics, c: Entity): Unit = {
    val lastFrame: Int = c.getCurrentSpriteConfig.nFrames - 1
    if (c.getCurrentFrame < lastFrame) {
      super.drawSprite(g, c)
    } else {
      val flipAdjustedPos = c.getFlipAdjustedPosition
      g.draw(c.getCurrentSpriteConfig.spritesheet.sprites(0)(lastFrame), flipAdjustedPos.x, flipAdjustedPos.y)
    }
  }
}
