package ch.hevs.gdx2d.isckombat.interface

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import io.AnsiColor._

class Scene {



  def renderScene(g: GdxGraphics, p1 : Player, p2 : Player): Unit = {
    val healthBarWidth = g.getScreenWidth.toFloat * 0.3f
    val healthBarHeight = 100
    val HealthBarXoffset = healthBarWidth / 2 + 50
    val healthBarYoffset = 1000
    val p2HealthBarPosX = g.getScreenWidth - healthBarWidth / 2 - 50
    val p1damagesoffset = healthBarWidth - (healthBarWidth * (p1.getHealth).toFloat / 1000.0f)
    val p2damagesoffset = healthBarWidth - (healthBarWidth * (p2.getHealth).toFloat / 1000.0f)
    g.drawFilledRectangle(HealthBarXoffset, healthBarYoffset, healthBarWidth, healthBarHeight,0, Color.RED)
    g.drawFilledRectangle(p2HealthBarPosX, healthBarYoffset, healthBarWidth, healthBarHeight,0, Color.RED)

    g.drawFilledRectangle(HealthBarXoffset - p1damagesoffset/2, healthBarYoffset, healthBarWidth * (p1.getHealth).toFloat / 1000.0f, healthBarHeight,0, Color.GREEN)
    g.drawFilledRectangle(p2HealthBarPosX + p2damagesoffset/2, healthBarYoffset, healthBarWidth * (p2.getHealth).toFloat / 1000.0f, healthBarHeight,0, Color.GREEN)

    println(healthBarWidth * (p1.getHealth).toFloat / 1000.0f)


  }

}
