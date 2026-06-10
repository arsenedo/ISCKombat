package ch.hevs.gdx2d.isckombat.entity.inputs

import scala.collection.mutable
import InputActions.{CROUCH, InputAction, JUMP, MOVE_LEFT, MOVE_RIGHT}
import com.badlogic.gdx.controllers.PovDirection

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

  def handleControllerButtonDown(i: Int): Unit = {
    if (InputConfigs.getXboxInputMap.contains(i)) {
      val action = InputConfigs.getXboxInputMap(i)
      if (togglable.contains(action)) {
        togglable(action) = true
      }
      addCommandToHistory(action)
    }
  }

  def handleControllerButtonUp(i: Int): Unit = {
    if (InputConfigs.getXboxInputMap.contains(i)) {
      val action = InputConfigs.getXboxInputMap(i)
      if (togglable.contains(action)) {
        togglable(action) = false
      }
    }
  }

  def handleControllerPovDirectionChange(povDirection: PovDirection): Unit = {
    for (togglableAction <- togglable.keys.toArray) {
      togglable(togglableAction) = false
    }

    if (povDirection == PovDirection.center) return

    val inputActions: Array[InputAction] = InputConfigs.controllerDirectionsMap(povDirection)

    for (inputAction <- inputActions) {
      if (togglable.contains(inputAction)) {
        togglable(inputAction) = true
      }
      addCommandToHistory(inputAction)
    }
  }

  private def addCommandToHistory(cmd : InputAction): Unit = {
    cmdHistory.enqueue(InputCommand(cmd))
    if (cmdHistory.length > QUEUE_MAX_LENGTH) {
      cmdHistory.dequeue

    }
  }
}
