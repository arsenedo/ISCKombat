package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction

class IdleState() extends PlayerState {

  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getIdleSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (c.isToggled(InputActions.MOVE_LEFT) || c.isToggled(InputActions.MOVE_RIGHT)) {
      val toggled: InputAction = if (c.isToggled(InputActions.MOVE_LEFT)) InputActions.MOVE_LEFT else InputActions.MOVE_RIGHT

      c.updateState(new WalkState(toggled))
      return
    }

    if (c.isToggled(InputActions.JUMP)) {
      c.updateState(new JumpState)
      return
    }

    if (c.tryExecuteLastCommand(
      InputActions.CROUCH,
      () => c.updateState(new CrouchState())
    )) {
      return
    }
    if (c.tryExecuteLastCommand(
      InputActions.BLOCK,
      () => c.updateState(new BlockState())
    )) {
      return
    }

    if (c.tryExecuteLastCommand(
      InputActions.PUNCH,
      () => c.updateState(new PunchState())
    )) {
      return
    }

  }

  override def exit(c: Player): Unit = {
  }
}
