package ch.hevs.gdx2d.isckombat.entity.inputs

import InputActions.InputAction
import com.badlogic.gdx.controllers.PovDirection

import scala.collection.mutable

trait Controllable {
  var inputsHandler  : InputHandler = new InputHandler

  def setInputs(inputsMap : mutable.HashMap[Int, InputAction]): Unit = {
    inputsHandler.setInputs(inputsMap)
  }
  def handleKeyDown(keycode: Int) = {
    inputsHandler.handleKeyDown(keycode)
  }
  def handleKeyUp(keycode: Int) = {
    inputsHandler.handleKeyUp(keycode)
  }

  def handleControllerButtonDown(i: Int) = {
    inputsHandler.handleControllerButtonDown(i)
  }

  def handleControllerButtonUp(i: Int): Unit = {
    inputsHandler.handleControllerButtonUp(i)
  }

  def handleControllerPovDirectionChange(povDirection: PovDirection): Unit = {
    inputsHandler.handleControllerPovDirectionChange(povDirection)
  }
}
