package ch.hevs.gdx2d.isckombat.entity.inputs

import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.{CROUCH, InputAction, JUMP, MOVE_LEFT, MOVE_RIGHT}
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.PovDirection

import scala.collection.mutable

object InputActions extends Enumeration {
  type InputAction = Value
  val JUMP, MOVE_LEFT, CROUCH, MOVE_RIGHT, PUNCH, BLOCK= Value
}

object InputConfigs {
  private val xboxInputs: mutable.HashMap[Int, InputActions.InputAction] = mutable.HashMap(
    Xbox.DPAD_UP -> InputActions.JUMP,
    Xbox.DPAD_LEFT -> InputActions.MOVE_LEFT,
    Xbox.DPAD_DOWN -> InputActions.CROUCH,
    Xbox.DPAD_RIGHT -> InputActions.MOVE_RIGHT,
    Xbox.X -> InputActions.PUNCH,
    Xbox.A -> InputActions.BLOCK
  )

  val controllerDirectionsMap: mutable.HashMap[PovDirection, Array[InputAction]] = mutable.HashMap(
    PovDirection.north -> Array(JUMP),
    PovDirection.northEast -> Array(MOVE_RIGHT, JUMP),
    PovDirection.east -> Array(MOVE_RIGHT),
    PovDirection.southEast -> Array(MOVE_RIGHT, CROUCH),
    PovDirection.south -> Array(CROUCH),
    PovDirection.southWest -> Array(MOVE_LEFT, CROUCH),
    PovDirection.west -> Array(MOVE_LEFT),
    PovDirection.northWest -> Array(MOVE_LEFT, JUMP)

  )

  private val player1Inputs: mutable.HashMap[Int, InputActions.InputAction] = mutable.HashMap(
    Input.Keys.W ->  InputActions.JUMP,
    Input.Keys.A -> InputActions.MOVE_LEFT,
    Input.Keys.S -> InputActions.CROUCH,
    Input.Keys.D -> InputActions.MOVE_RIGHT,
    Input.Keys.F -> InputActions.PUNCH,
    Input.Keys.T -> InputActions.BLOCK

  )

  private val player2Inputs: mutable.HashMap[Int, InputActions.InputAction] = mutable.HashMap(
    Input.Keys.UP -> InputActions.JUMP,
    Input.Keys.LEFT -> InputActions.MOVE_LEFT,
    Input.Keys.DOWN -> InputActions.CROUCH,
    Input.Keys.RIGHT -> InputActions.MOVE_RIGHT,
    Input.Keys.P -> InputActions.PUNCH,
    Input.Keys.L -> InputActions.BLOCK
  )

  def getPlayer1InputMap :mutable.HashMap[Int, InputActions.InputAction] = {
    player1Inputs
  }

  def getPlayer2InputMap :mutable.HashMap[Int, InputActions.InputAction] = {
    player2Inputs
  }

  def getXboxInputMap = {
    xboxInputs
  }
}
