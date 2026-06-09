package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.isckombat.entity.{Entity, Hitbox, MichaelJackson, Player, Scorpion}
import ch.hevs.gdx2d.isckombat.collision.CollisionHandler
import ch.hevs.gdx2d.isckombat.entity.inputs.InputConfigs
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.interface.Scene
import ch.hevs.gdx2d.isckombat.state.playerStates.{HitState, KnockoutState, VictoryState}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable
import scala.util.Random

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1920, 1080){
  private val DEBUG_MODE: Boolean = true

  private var player1: Player = _
  private var player2: Player = _
  private var gameEnded: Boolean = false
  private var scene : Scene = _

  private var background: BitmapImage = _

  private var activeMusic: MusicPlayer = _

  override def onInit(): Unit = {
    scene = new Scene
    player1 = new Scorpion(0, new Vector2(50,100))
    player2 = new MichaelJackson(1, new Vector2(getWindowWidth - 200, 100))

    player1.setInputs(InputConfigs.getPlayer1InputMap)
    player2.setInputs(InputConfigs.getPlayer2InputMap)

    player1.enemyId = player2.id
    player2.enemyId = player1.id

    EntityRegister.addEntity(player1)
    EntityRegister.addEntity(player2)

    background = new BitmapImage("data/images/bg.jpg")

    val bgMusic: Array[MusicPlayer] = Array(
      new MusicPlayer("data/music/06. Warrior Shrine.mp3"),
      new MusicPlayer("data/music/05. Palace Gates.mp3"),
      new MusicPlayer("data/music/08. Throne Room.mp3"),
    )

    activeMusic = bgMusic(Random.between(0, bgMusic.length))
    activeMusic.setVolume(0.25f)
    activeMusic.loop()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    onLogicUpdate()

    g.clear()

    g.drawPicture(getWindowWidth/2, getWindowHeight/2 + 50, background)

    EntityRegister.entities.foreach((entity) => entity.drawSprite(g))
    scene.renderScene(g, player1, player2)



    if (DEBUG_MODE) {
      drawDebugBoxes(g)
      g.drawFPS(Color.BLUE)
    }
  }

  private def onLogicUpdate(): Unit = {
    simulationPhase()

    detectAndApplyCollisions()

    detectGameEnd()
  }

  private def simulationPhase(): Unit = {
    player1.update(player2.position)
    player2.update(player1.position)

    EntityRegister.entities.filter(entity => !entity.isInstanceOf[Player]).foreach(entity => entity.update())
  }

  private def detectAndApplyCollisions(): Unit = {
    val detectedCollisions: mutable.HashMap[Hitbox, Entity] = CollisionHandler.detectCollisions(Array(player1, player2))

    detectedCollisions.foreachEntry((hitbox, target) => {
      target.asInstanceOf[Player].receiveDamage(50)
      hitbox.isActive = false
    })
  }

  private def detectGameEnd(): Unit = {
    if (gameEnded) return
    if (player1.getHealth <= 0) {
      player1.updateState(new KnockoutState())
      player2.updateState(new VictoryState())
      gameEnded = true
    } else if (player2.getHealth <= 0) {
      player2.updateState(new KnockoutState())
      player1.updateState(new VictoryState())
      gameEnded = true
    }

    if (gameEnded) {
      activeMusic.stop()
    }
  }

  override def onKeyDown(keycode: Int): Unit = {
    handleKeyDown(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    handleKeyUp(keycode)
  }

  private def handleKeyDown(keycode: Int): Unit = {
    player1.handleKeyDown(keycode)
    player2.handleKeyDown(keycode)
  }

  private def handleKeyUp(keycode: Int): Unit = {
    player1.handleKeyUp(keycode)
    player2.handleKeyUp(keycode)
  }

  private def drawDebugBoxes(g: GdxGraphics): Unit = {
    EntityRegister.entities.foreach(entity => entity.drawDebugBoxes(g))
  }
}
