package ch.hevs.gdx2d.isckombat.state.gameState

import ch.hevs.gdx2d.isckombat.Game
import ch.hevs.gdx2d.isckombat.state.State
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.controllers.{Controller, PovDirection}

trait GameState extends State[Game] {
  def onGraphicsRender(g: GdxGraphics, game: Game): Unit
  def onHandleKeyDown(key: Int): Unit
  def onHandleKeyUp(key: Int): Unit
  def handleControllerButtonDown(game: Game, controller: Controller, i: Int): Unit
  def handleControllerButtonUp(game: Game, controller: Controller, i: Int): Unit
  def handleControllerPovMoved(game: Game, controller: Controller, povDirection: PovDirection): Unit
}
