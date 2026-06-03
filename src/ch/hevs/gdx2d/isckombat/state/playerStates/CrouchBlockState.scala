package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class CrouchBlockState extends PlayerState {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getCrouchBlockSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (!c.isToggled(InputActions.BLOCK)) {
      c.updateState(new CrouchState())
    }
    if (!c.isToggled(InputActions.CROUCH)){
      c.updateState(new BlockState())
    }
  }

  override def receiveDamage(player: Player, damage: Int): Unit = {
    val ajusted : Int = ((damage).toDouble * 0.1).toInt
    val direction : Int = if (player.getCurrentSpriteFrame.isFlipX) 1 else -1
    player.applyDamage(ajusted)
    player.position.add(new Vector2(20 * direction, 0))
  }

  override def exit(c: Player): Unit = {
  }
}
