package ch.hevs.gdx2d.research

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

object ResearchHello {
  def main(args: Array[String]): Unit = {
    new ResearchHello(1920, 1080)
  }
}

class ResearchHello(width: Int, height: Int) extends PortableApplication(width, height) {
  var rectPosition: Vector2 = new Vector2(0, 0)
  val rectWidth: Int = 87
  val rectHeight: Int = 136
  var isMoving: Boolean = false
  var spritesheet: Spritesheet = null;
  var currentFrame = 0
  var dt = 0.0
  var nFrames = 9
  var FRAME_TIME = 0.1
  var direction: Int = 1
  override def onInit(): Unit = {
    setTitle("Research Time")
    rectPosition = new Vector2(200, (getWindowHeight.toDouble * 0.2).toInt)
    spritesheet = new Spritesheet("data/images/Walk.png", 87, 136)
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
    g.clear()

    dt += Gdx.graphics.getDeltaTime

    // Do we have to display the next frame// Do we have to display the next frame
    if (dt > FRAME_TIME) {
      dt = 0
      currentFrame = (currentFrame + 1) % nFrames

      Update()
    }

    g.drawFilledRectangle(rectPosition.x + rectWidth / 2, rectPosition.y + rectHeight / 2, rectWidth, rectHeight, 0, Color.LIME)

    g.draw(spritesheet.sprites(0)(currentFrame), rectPosition.x, rectPosition.y) // Centered

    g.drawFPS()
  }
}
