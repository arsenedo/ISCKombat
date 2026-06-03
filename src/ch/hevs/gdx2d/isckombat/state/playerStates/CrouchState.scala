package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.lib.GdxGraphics

class CrouchState() extends PlayerState {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getCrouchSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (!c.isToggled(InputActions.CROUCH)){
      c.updateState(new IdleState())
    }
    c.tryExecuteLastCommand(
      InputActions.PUNCH,
      () => c.updateState(new CrouchKickState())
    )

  }

  override def exit(c: Player): Unit = {

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
