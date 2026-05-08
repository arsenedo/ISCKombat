package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game() extends PortableApplication(1920, 1080){
  override def onInit(): Unit = {

  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
  }

  override def onKeyDown(keycode: Int): Unit = {
    println(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    println(keycode)
  }
}
