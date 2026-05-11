package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.isckombat.character.{Character, MichaelJackson, Scorpion}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1600, 900){
  private var player1: Character = _
  private var player2: Character = _

  private val player1Inputs: Array[Int] = Array(
    Input.Keys.W,
    Input.Keys.A,
    Input.Keys.S,
    Input.Keys.D,
    Input.Keys.U,
    Input.Keys.I,
    Input.Keys.O
  )

  private val player2Inputs: Array[Int] = Array(
    Input.Keys.UP,
    Input.Keys.LEFT,
    Input.Keys.DOWN,
    Input.Keys.RIGHT,
    Input.Keys.NUMPAD_4,
    Input.Keys.NUMPAD_5,
    Input.Keys.NUMPAD_6
  )
  override def onInit(): Unit = {
    player1 = new Scorpion(new Vector2(50,100))
    player2 = new MichaelJackson(new Vector2(getWindowWidth - 200, 100))
  }

  def update(): Unit = {
    player1.update()
    player2.update()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    update()

    g.clear()

    player1.drawSprite(g)

    player2.drawSprite(g)
  }

  override def onKeyDown(keycode: Int): Unit = {
    // TODO MAP KEYCODES TO ACTIONS BEFORE PASSING THEM TO THE PLAYERS
    // TODO DECIDE ON AN ACTION BUFFERING STRATEGY
    println(keycode)
    handleKeyDown(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    // TODO MAP KEYCODES TO ACTIONS BEFORE PASSING THEM TO THE PLAYERS
    // TODO DECIDE ON AN ACTION BUFFERING STRATEGY
    println(keycode)
    handleKeyUp(keycode)
  }

  private def handleKeyDown(keycode: Int): Unit = {
    if (player1Inputs.contains(keycode)) {
      player1.handleKeyDown(keycode)
    } else if (player2Inputs.contains(keycode)) {
      player2.handleKeyDown(keycode)
    }
  }

  private def handleKeyUp(keycode: Int): Unit = {
    if (player1Inputs.contains(keycode)) {
      player1.handleKeyUp(keycode)
    } else if (player2Inputs.contains(keycode)) {
      player2.handleKeyUp(keycode)
    }
  }
}
