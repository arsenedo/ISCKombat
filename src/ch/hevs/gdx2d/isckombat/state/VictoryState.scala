package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity.Entity
import ch.hevs.gdx2d.lib.GdxGraphics

class VictoryState extends State {

  override def enter(c: Entity): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getVictorySpritesheet)
  }

  override def update(c: Entity): Unit = {

  }

  override def exit(c: Entity): Unit = {

  }

  override def handleKeyDown(keycode: Int, c: Entity): Unit = {

  }

  override def handleKeyUp(keycode: Int, c: Entity): Unit = {

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
