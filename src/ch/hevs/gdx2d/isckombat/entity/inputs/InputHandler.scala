package ch.hevs.gdx2d.isckombat.entity.inputs

import scala.collection.mutable
import InputActions.InputAction

class InputHandler {
  private var playerInputs : mutable.HashMap[Int, InputAction] = _
  private val cmdHistory : mutable.Queue[InputCommand] = new mutable.Queue[InputCommand]()
  private val QUEUE_MAX_LENGTH = 10
  private val togglable : mutable.HashMap[InputAction, Boolean] = mutable.HashMap(
    InputActions.MOVE_LEFT -> false,
    InputActions.MOVE_RIGHT -> false,
    InputActions.JUMP -> false,
    InputActions.BLOCK -> false,
    InputActions.CROUCH -> false
  )

  def setInputs(inputsMap: mutable.HashMap[Int, InputAction]): Unit = {
    playerInputs = inputsMap
  }

  def getHistory: mutable.Queue[InputCommand] = {
    cmdHistory
  }

  def isToggled(action: InputAction) = {
    val togglableOption: Option[Boolean] = togglable.get(action)

    if (togglableOption.isDefined) {
      togglableOption.get
    } else false
  }

  def handleKeyDown(keycode : Int): Unit = {
    if (playerInputs.contains(keycode)){
      val action = playerInputs(keycode)
      if (togglable.contains(action)) {
        togglable(action) = true
      }
      addCommandToHistory(action)
    }
  }

  def handleKeyUp(keycode: Int): Unit = {
    if (playerInputs.contains(keycode)) {
      val action = playerInputs(keycode)
      if (togglable.contains(action)) {
        togglable(action) = false
      }
    }
  }

  private def addCommandToHistory(cmd : InputAction): Unit = {
    cmdHistory.enqueue(InputCommand(cmd))
    if (cmdHistory.length > QUEUE_MAX_LENGTH) {
      cmdHistory.dequeue

    }
  }
}
