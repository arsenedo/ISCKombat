package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.sprites.SpriteConfig
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class FreezeState extends PlayerState {

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(owner.getSpritesLoader.getHitSpritesheet)
  }

  override def update(owner: Player): Unit = {}

  override def exit(owner: Player): Unit = {}

  override def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val flipAdjustedPos: Vector2 = player.getFlipAdjustedPosition
    val currentSpriteConfig: SpriteConfig = player.getCurrentSpriteConfig
    g.draw(currentSpriteConfig.spritesheet.sprites(0)(currentSpriteConfig.nFrames - 1), flipAdjustedPos.x, flipAdjustedPos.y)
  }
}

