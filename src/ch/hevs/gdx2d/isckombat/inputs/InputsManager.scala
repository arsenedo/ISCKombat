package ch.hevs.gdx2d.isckombat.inputs

import com.badlogic.gdx.{Gdx, Input}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class InputsManager {
  val player1ActionsToPerform = new ArrayBuffer[Actions.Value]()
  val player2ActionsToPerform = new ArrayBuffer[Actions.Value]()


  def get_action(p : mutable.HashMap[Int, Actions.Value]) = {
    p.foreach

  }
  val player1Inputs: mutable.HashMap[Int, Actions.Value] = mutable.HashMap(
    Input.Keys.W ->  Actions.JUMP,
    Input.Keys.A -> Actions.MOVE_LEFT,
    Input.Keys.S -> Actions.CROUCH,
    Input.Keys.D -> Actions.MOVE_RIGHT,
    Input.Keys.U -> Actions.PUNCH,
  )

  private val player2Inputs: mutable.HashMap[Int, Actions.Value] = mutable.HashMap(
    Input.Keys.UP -> Actions.JUMP,
    Input.Keys.LEFT -> Actions.MOVE_LEFT,
    Input.Keys.DOWN -> Actions.CROUCH,
    Input.Keys.RIGHT -> Actions.MOVE_RIGHT,
    Input.Keys.NUMPAD_4 -> Actions.PUNCH,
  )



}

object test extends App {

  val inputsManager = new InputsManager
    if (inputsManager.player1Inputs(Input.Keys.U) == Actions.PUNCH)
      println("action")
  }
}
