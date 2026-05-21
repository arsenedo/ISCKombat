package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.isckombat.entity.{Entity, Hitbox, MichaelJackson, Scorpion}
import ch.hevs.gdx2d.isckombat.collision.CollisionHandler
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.state.HitState
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1920, 1080){
  val DEBUG_MODE: Boolean = true

  private var player1: Entity = _
  private var player2: Entity = _

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
    player1 = new Scorpion(0, new Vector2(50,100))
    player2 = new MichaelJackson(1, new Vector2(200, 100))

    EntityRegister.addEntity(player1)
    EntityRegister.addEntity(player2)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    onLogicUpdate()

    g.clear()
    g.drawFPS(Color.RED)
    player1.drawSprite(g)

    player2.drawSprite(g)

    if (DEBUG_MODE) {
      drawDebugBoxes(g)
    }
  }

  private def onLogicUpdate(): Unit = {
    simulationPhase()

    detectAndApplyCollisions()
  }

  private def simulationPhase(): Unit = {
   player1.update(player2.position)
   player2.update(player1.position)
  }

  private def detectAndApplyCollisions(): Unit = {
    val detectedCollisions: mutable.HashMap[Hitbox, Entity] = CollisionHandler.detectCollisions(Array(player1, player2))

    detectedCollisions.foreachEntry((hitbox, target) => {
      target.updateState(new HitState(50))
      hitbox.isActive = false
    })
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

  private def drawDebugBoxes(g: GdxGraphics): Unit = {
    player1.drawDebugBoxes(g)
    player2.drawDebugBoxes(g)
  }
}
