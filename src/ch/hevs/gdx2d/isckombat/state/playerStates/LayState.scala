package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.sprites.SpriteConfig
import ch.hevs.gdx2d.lib.GdxGraphics

class LayState(durationTicks: Int) extends PlayerState {
  var enterTick: Int = 0

  override def enter(owner: Player): Unit = {
    val fallingSpritesheet: SpriteConfig = owner.getSpritesLoader.getFallingSpritesheet
    owner.updateSpritesheet(fallingSpritesheet)

    enterTick = owner.getCurrentTick
  }

  override def update(owner: Player): Unit = {
    if (owner.getCurrentTick >= enterTick + durationTicks) {
      owner.updateState(new IdleState)
    }
  }

  override def exit(owner: Player): Unit = {

  }

  override def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val flipAdjustedPos = player.getFlipAdjustedPosition
    val currentSpriteConfig: SpriteConfig = player.getCurrentSpriteConfig

    g.draw(currentSpriteConfig.spritesheet.sprites(0)(currentSpriteConfig.nFrames - 1), flipAdjustedPos.x, flipAdjustedPos.y)
  }

  override def receiveDamage(player: Player, damage: Int): Unit = {

  }
}
