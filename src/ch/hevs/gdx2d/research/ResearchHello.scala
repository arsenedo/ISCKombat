package ch.hevs.gdx2d.research

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

object ResearchHello {
  def main(args: Array[String]): Unit = {
    new ResearchHello(1920, 1080)
  }
}

class ResearchHello(width: Int, height: Int) extends PortableApplication(width, height) {
  var rectPosition: Vector2 = new Vector2(0, 0)
  val rectWidth: Int = 100
  val rectHeight: Int = 250
  var isMoving: Boolean = false
  var direction: Int = 1
  override def onInit(): Unit = {
    setTitle("Research Time")
    rectPosition = new Vector2(200, (getWindowHeight.toDouble * 0.2).toInt)
  }

  override def onKeyDown(keycode: Int): Unit = {
    if (keycode == 32 || keycode == 22 || keycode == 29 || keycode == 21) {
      isMoving = true
      if (keycode == 29 || keycode == 21) direction = -1 else direction = 1
    }
  }

  override def onKeyUp(keycode: Int): Unit = {
    if (keycode == 32 || keycode == 22 || keycode == 29 || keycode == 21) {
      isMoving = false
    }
  }

  private def Update(): Unit = {
    if (isMoving) {
      rectPosition.add(new Vector2(direction * 10, 0))
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    Update()

    g.clear()

    g.drawFilledRectangle(rectPosition.x, rectPosition.y, rectWidth, rectHeight, 0, Color.LIME)

    g.drawFPS()
  }
}
