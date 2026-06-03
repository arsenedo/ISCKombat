package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.state.State
import ch.hevs.gdx2d.lib.GdxGraphics

trait PlayerState extends State[Player] {
  def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val flipAdjustedPos = player.getFlipAdjustedPosition
    g.draw(player.getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }

  def receiveDamage(player: Player, damage: Int): Unit = {
    player.updateState(new HitState(damage))
  }
}
