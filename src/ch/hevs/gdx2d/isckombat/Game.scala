package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.isckombat.character.{Character, Scorpion}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1280, 720){
  private var player1: Character = _
  override def onInit(): Unit = {
    player1 = new Scorpion(new Vector2(0,0))
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    player1.drawSprite(g)
  }

  override def onKeyDown(keycode: Int): Unit = {
    println(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    println(keycode)
  }
}
