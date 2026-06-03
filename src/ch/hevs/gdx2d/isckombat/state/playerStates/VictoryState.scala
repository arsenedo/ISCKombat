package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.lib.GdxGraphics

class VictoryState extends PlayerState {

  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getVictorySpritesheet)

    c.tryPlaySoundOnCurrentFrame()
  }

  override def update(c: Player): Unit = {

  }

  override def exit(c: Player): Unit = {

  }

  override def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val lastFrame: Int = player.getCurrentSpriteConfig.nFrames - 1
    if (player.getCurrentFrame < lastFrame) {
      super.drawSprite(g, player)
    } else {
      val flipAdjustedPos = player.getFlipAdjustedPosition
      g.draw(player.getCurrentSpriteConfig.spritesheet.sprites(0)(lastFrame), flipAdjustedPos.x, flipAdjustedPos.y)
    }
  }
}
