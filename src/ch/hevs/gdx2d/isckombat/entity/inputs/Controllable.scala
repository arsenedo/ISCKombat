package ch.hevs.gdx2d.isckombat.entity.inputs

import InputActions.InputAction

import scala.collection.mutable

trait Controllable {
  protected var inputsHandler  : InputHandler = new InputHandler

  def setInputs(inputsMap : mutable.HashMap[Int, InputAction]): Unit = {
    inputsHandler.setInputs(inputsMap)
  }
  def handleKeyDown(keycode: Int) = {
    inputsHandler.handleKeyDown(keycode)
  }
  def handleKeyUp(keycode: Int) = {
    inputsHandler.handleKeyUp(keycode)
  }
}
