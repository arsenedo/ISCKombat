package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.isckombat.character.{Character, MichaelJackson, Scorpion}
import ch.hevs.gdx2d.isckombat.inputs.InputsManager
import ch.hevs.gdx2d.isckombat.state.HitState
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1600, 900){
  val DEBUG_MODE: Boolean = true

  private var player1: Character = _
  private var player2: Character = _
  private var inputs_manager : InputsManager = new InputsManager()


  def inputsManager(): Unit = {

  }
  override def onInit(): Unit = {
    player1 = new Scorpion(new Vector2(50,100))
    player2 = new MichaelJackson(new Vector2(getWindowWidth - 200, 100))
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    onLogicUpdate()

    g.clear()

    player1.drawSprite(g)

    player2.drawSprite(g)

    if (DEBUG_MODE) {
      drawDebugBoxes(g)
    }
  }

  private def onLogicUpdate(): Unit = {

    inputsManager()//renvoi d'un arraybuffer d'actions (pas sur)
    player1.update(player2.position)
    player2.update(player1.position)

    performAttacks(player1, player2)
    performAttacks(player2, player1)
  }

  override def onKeyDown(keycode: Int): Unit = {
    // TODO MAP KEYCODES TO ACTIONS BEFORE PASSING THEM TO THE PLAYERS
    // TODO DECIDE ON AN ACTION BUFFERING STRATEGY
    println(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    // TODO MAP KEYCODES TO ACTIONS BEFORE PASSING THEM TO THE PLAYERS
    // TODO DECIDE ON AN ACTION BUFFERING STRATEGY
    println(keycode)

  }



  private def drawDebugBoxes(g: GdxGraphics): Unit = {
    player1.drawDebugBoxes(g)
    player2.drawDebugBoxes(g)
  }

  private def performAttacks(attacker: Character, attacked: Character): Unit = {
    val attackerHitboxOption = attacker.getHitbox
    if (attackerHitboxOption.isDefined) {
      val hitbox = attacker.getHitbox.get
      val attackedPosition = attacked.getFlipAdjustedPosition
      val attackedSprite = attacked.getCurrentSpriteFrame

      if (hitbox.isCollidingWith(attackedPosition, attackedSprite.getRegionWidth, attackedSprite.getRegionHeight)) {
        attacked.updateState(new HitState(50))
      }
    }
  }
}
