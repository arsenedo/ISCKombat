package ch.hevs.gdx2d.hello

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.{Interpolation, Vector2}
import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsCircle, PhysicsPolygon, PhysicsStaticBox}
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.graphics.GeomUtils
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.physics.box2d.World



/**
 * Hello World demo in Scala
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
object HelloWorldScala {

  def main(args: Array[String]): Unit = {
    new HelloWorldScala
  }
}

class HelloWorldScala extends PortableApplication(1920,1080) {
  private var world: World = _
  private var p1: PhysicsBox = _
  private var debugRenderer: DebugRenderer = _

  override def onInit(): Unit = {
    setTitle("Simple physics simulation, mui 2013")

    world = PhysicsWorld.getInstance()


    val w = getWindowWidth
    val h = getWindowHeight

    // Build the walls around the screen
    new PhysicsScreenBoundaries(w.toFloat, h.toFloat)

    // The slope on which the objects roll
    new PhysicsStaticBox("slope", new Vector2(w / 2f, h / 2f), (w / 3f * 2f), 16f, math.Pi.toFloat / 12.0f)


    // Build the dominoes
    p1 = new PhysicsBox("player", new Vector2(60f, 120f), 50f, 150f)



    debugRenderer = new DebugRenderer()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    p1.setBodyLinearVelocity(1f,0.5f)
    debugRenderer.render(world, g.getCamera.combined)
    PhysicsWorld.updatePhysics(Gdx.graphics.getRawDeltaTime)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}
