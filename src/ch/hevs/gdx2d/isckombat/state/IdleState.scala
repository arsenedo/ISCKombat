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

    // TODO THINK SOMETHING ABOUT CODE DUPLICATES
    // maybe the state should define the list of available commands on enter?
    val latestCommandOption = c.getLatestCommand
    if (latestCommandOption.isEmpty) return
    val latestCommand = latestCommandOption.get
    if (latestCommand.action == InputActions.PUNCH && !latestCommand.isExecuted) {
      c.updateState(new PunchState())
      latestCommand.isExecuted = true
    }

  }

  override def exit(c: Player): Unit = {
  }
}
