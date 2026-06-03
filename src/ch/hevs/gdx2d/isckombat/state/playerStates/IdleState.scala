package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.state.playerStates.Crouch.CrouchState

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
    } else if (c.isToggled(InputActions.CROUCH)) {
      c.updateState(new CrouchState())
    } else if (c.isToggled(InputActions.BLOCK)) {
      c.updateState(new BlockState)
    } else if (c.tryExecuteLastCommand(
      InputActions.PUNCH,
      () => c.updateState(new PunchState())
    )) {
      return
    }

  }

  override def exit(c: Player): Unit = {
  }
}
