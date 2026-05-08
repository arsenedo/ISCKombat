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
  var spritesheetIdle: Spritesheet = _
  var spritesheetWalk: Spritesheet = _
  var currentFrame = 0
  var dt = 0.0
  var nFramesWalk = 9
  var nFramesIdle = 7
  var FRAME_TIME = 0.07

  val colliderX = 500
  val colliderY = 0
  val colliderWidth = 200
  val colliderHeight = 200

  var direction: Int = 1
  override def onInit(): Unit = {
    setTitle("Research Time")
    spritesheetIdle = new Spritesheet("data/images/Idle.png", 152, 268)
    spritesheetWalk = new Spritesheet("data/images/Walk.png", 174, 272)
  }

  override def onKeyDown(keycode: Int): Unit = {
    if (keycode == 32 || keycode == 22 || keycode == 29 || keycode == 21) {
      isMoving = true
      dt = 0
      currentFrame = 0
      if (keycode == 29 || keycode == 21) direction = -1 else direction = 1
    }
  }

  override def onKeyUp(keycode: Int): Unit = {
    if (keycode == 32 || keycode == 22 || keycode == 29 || keycode == 21) {
      dt = 0
      currentFrame = 0
      isMoving = false
    }
  }

  private def Update(): Unit = {
    if (isMoving) {
      rectPosition.add(new Vector2(direction * 20, 0))
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    dt += Gdx.graphics.getDeltaTime

    // Do we have to display the next frame// Do we have to display the next frame
    if (dt > FRAME_TIME) {
      dt = 0
      currentFrame = (currentFrame + 1) % (if (isMoving) nFramesWalk else nFramesIdle)

      Update()
    }
    val activeSprite = if (isMoving) spritesheetWalk else spritesheetIdle

    // Collision example
    val isColliding: Boolean = rectPosition.x + activeSprite.sprites(0)(currentFrame).getRegionWidth >= colliderX && rectPosition.x <= colliderX + colliderWidth
    g.drawFilledRectangle(colliderX + colliderWidth / 2, colliderY + colliderHeight / 2, colliderWidth, colliderHeight, 0, if (isColliding) Color.RED else Color.GREEN)

    // Hitbox example
    g.drawFilledRectangle(rectPosition.x + activeSprite.sprites(0)(currentFrame).getRegionWidth / 2, rectPosition.y + activeSprite.sprites(0)(currentFrame).getRegionHeight / 2, activeSprite.sprites(0)(currentFrame).getRegionWidth, activeSprite.sprites(0)(currentFrame).getRegionHeight, 0, Color.LIME)

    // Spritesheet example
    g.draw(activeSprite.sprites(0)(currentFrame), rectPosition.x, rectPosition.y)

    g.drawFPS()
  }
}
