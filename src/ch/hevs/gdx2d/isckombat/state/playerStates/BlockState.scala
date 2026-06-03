package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class BlockState extends PlayerState {

  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getBlockSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (!c.isToggled(InputActions.BLOCK)) {
      c.updateState(new IdleState())
    }

    if (c.tryExecuteLastCommand(
      InputActions.CROUCH,
      () => c.updateState(new CrouchBlockState())
    )) {
      return
    }
  }

  override def exit(c: Player): Unit = {
  }

  override def receiveDamage(player: Player, damage: Int): Unit = {
    val ajusted : Int = ((damage).toDouble * 0.1).toInt
    val direction : Int = if (player.getCurrentSpriteFrame.isFlipX) 1 else -1
    player.applyDamage(ajusted)
    player.position.add(new Vector2(20 * direction, 0))
  }
  override def drawSprite(g: GdxGraphics, c: Player): Unit = {
    val lastFrame: Int = c.getCurrentSpriteConfig.nFrames - 1
    if (c.getCurrentFrame < lastFrame) {
      super.drawSprite(g, c)
    } else {
      val flipAdjustedPos = c.getFlipAdjustedPosition
      g.draw(c.getCurrentSpriteConfig.spritesheet.sprites(0)(lastFrame), flipAdjustedPos.x, flipAdjustedPos.y)
    }
  }
}
