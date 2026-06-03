package ch.hevs.gdx2d.isckombat.state.playerStates.Crouch

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.state.playerStates.{IdleState, PlayerState}

class CrouchState() extends PlayerState {
  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getCrouchSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (!c.isToggled(InputActions.CROUCH)){
      c.updateState(new IdleState())
    }
    if (c.tryExecuteLastCommand(
      InputActions.BLOCK,
      () => c.updateState(new CrouchBlockState())
    )) {
      return
    }

    c.tryExecuteLastCommand(
      InputActions.PUNCH,
      () => c.updateState(new CrouchKickState())
    )

  }

  override def exit(c: Player): Unit = {

  }

  override def receiveDamage(player: Player, damage: Int): Unit = {
    player.updateState(new CrouchHitState(damage))
  }
}
