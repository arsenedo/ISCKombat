package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.isckombat.character.{Character, MichaelJackson, Scorpion}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1600, 900){
  private var player1: Character = _
  override def onInit(): Unit = {
    player1 = new Scorpion(new Vector2(0,0))
  }

  def update(): Unit = {
    player1.update()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    update()

    g.clear()

    player1.drawSprite(g)
  }

  override def onKeyDown(keycode: Int): Unit = {
    // TODO MAP KEYCODES TO ACTIONS BEFORE PASSING THEM TO THE PLAYERS
    // TODO DECIDE ON AN ACTION BUFFERING STRATEGY
    println(keycode)
    player1.handleKeyDown(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    // TODO MAP KEYCODES TO ACTIONS BEFORE PASSING THEM TO THE PLAYERS
    // TODO DECIDE ON AN ACTION BUFFERING STRATEGY
    println(keycode)
    player1.handleKeyUp(keycode)
  }
}
