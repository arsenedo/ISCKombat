package ch.hevs.gdx2d.isckombat.state.gameState

import ch.hevs.gdx2d.isckombat.Game
import ch.hevs.gdx2d.isckombat.state.State
import ch.hevs.gdx2d.lib.GdxGraphics

trait GameState extends State[Game] {
  def onGraphicsRender(g: GdxGraphics, game: Game): Unit
  def onHandleKeyDown(key: Int): Unit
  def onHandleKeyUp(key: Int): Unit
}
