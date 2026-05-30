package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction

class IdleState() extends State[Player] {

  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getIdleSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (c.isToggled(InputActions.MOVE_LEFT) || c.isToggled(InputActions.MOVE_RIGHT)) {
      val toggled: InputAction = if (c.isToggled(InputActions.MOVE_LEFT)) InputActions.MOVE_LEFT else InputActions.MOVE_RIGHT

      c.updateState(new WalkState(toggled))
    }

    c.tryExecuteLastCommand(
      InputActions.PUNCH,
      () => c.updateState(new PunchState())
    )

  }

  override def exit(c: Player): Unit = {
  }
}
