package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import com.badlogic.gdx.math.Vector2

class WalkState(private var executorAction: InputAction) extends State[Player] {
  private var direction = if (executorAction == InputActions.MOVE_RIGHT) 1 else -1
  private val translationVector = new Vector2(5, 0)

  override def enter(c: Player): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getWalkSpritesheet)
  }

  override def update(c: Player): Unit = {
    if (!c.isToggled(executorAction)) {
      c.updateState(new IdleState)
    }

    c.position.add(translationVector.x * direction, translationVector.y)

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
