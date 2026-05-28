package ch.hevs.gdx2d.isckombat.entity.inputs

import com.badlogic.gdx.Input

import scala.collection.mutable

object InputActions extends Enumeration {
  type InputAction = Value
  val JUMP, MOVE_LEFT, CROUCH, MOVE_RIGHT, PUNCH, BLOCK= Value
}

object InputConfigs {
  private val player1Inputs: mutable.HashMap[Int, InputActions.InputAction] = mutable.HashMap(
    Input.Keys.W ->  InputActions.JUMP,
    Input.Keys.A -> InputActions.MOVE_LEFT,
    Input.Keys.S -> InputActions.CROUCH,
    Input.Keys.D -> InputActions.MOVE_RIGHT,
    Input.Keys.U -> InputActions.PUNCH,
  )

  private val player2Inputs: mutable.HashMap[Int, InputActions.InputAction] = mutable.HashMap(
    Input.Keys.UP -> InputActions.JUMP,
    Input.Keys.LEFT -> InputActions.MOVE_LEFT,
    Input.Keys.DOWN -> InputActions.CROUCH,
    Input.Keys.RIGHT -> InputActions.MOVE_RIGHT,
    Input.Keys.NUMPAD_4 -> InputActions.PUNCH,
  )
  def getPlayer1InputMap :mutable.HashMap[Int, InputActions.InputAction] = {
    player1Inputs
  }

  def getPlayer2InputMap :mutable.HashMap[Int, InputActions.InputAction] = {
    player2Inputs
  }
}
