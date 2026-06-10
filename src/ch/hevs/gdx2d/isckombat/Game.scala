package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.{PortableApplication, Xbox}
import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.interface.{Scene, StagesLoader}
import ch.hevs.gdx2d.isckombat.state.gameState.{GameState, MenuState}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.controllers.{Controller, ControllerListener, Controllers, PovDirection}
import com.badlogic.gdx.math.{Vector2, Vector3}

import scala.collection.mutable

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1920, 1080){
  private var gameState: GameState = new MenuState

  override def onInit(): Unit = {
    gameState.enter(this)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    gameState.update(this)

    g.clear()

    gameState.onGraphicsRender(g, this)
  }

  def updateGameState(newState: GameState): Unit = {
    gameState.exit(this)

    gameState = newState

    newState.enter(this)
  }

  override def onKeyDown(keycode: Int): Unit = {
    gameState.onHandleKeyDown(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    gameState.onHandleKeyUp(keycode)
  }
}
