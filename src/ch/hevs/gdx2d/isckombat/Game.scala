package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.components.audio.{MusicPlayer, SoundSample}
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.{PortableApplication, Xbox}
import ch.hevs.gdx2d.isckombat.entity.{Dio, Entity, Hitbox, MichaelJackson, Player, Scorpion}
import ch.hevs.gdx2d.isckombat.collision.CollisionHandler
import ch.hevs.gdx2d.isckombat.entity.inputs.InputConfigs
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.interface.{Scene, StagesLoader}
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.ScorpionSpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.{HitState, KnockoutState, VictoryState}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.{Controller, ControllerListener, Controllers, PovDirection}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Vector2, Vector3}

import scala.collection.mutable
import scala.util.Random

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1920, 1080){
  private val DEBUG_MODE: Boolean = false

  private var player1: Player = _
  private var player2: Player = _
  private var gameEnded: Boolean = false
  private var scene : Scene = _

  private var controllerMap: mutable.LinkedHashMap[Player, Option[Controller]] = _

  override def onInit(): Unit = {
    scene = new Scene(StagesLoader.createRandomStage)
    player1 = new Scorpion(0, new Vector2(50,100))
    player2 = new Dio(1, new Vector2(getWindowWidth - 200, 100))

    player1.setInputs(InputConfigs.getPlayer1InputMap)
    player2.setInputs(InputConfigs.getPlayer2InputMap)

    player1.enemyId = player2.id
    player2.enemyId = player1.id

    EntityRegister.addEntity(player1)
    EntityRegister.addEntity(player2)

    controllerMap = mutable.LinkedHashMap(
      player1 -> None,
      player2 -> None
    )

    setupControllerListeners()

    new SoundSample("data/sounds/misc/round1_fight.wav").play()

    scene.startMusic()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    onLogicUpdate()

    g.clear()

    scene.renderScene(g, player1, player2)

    EntityRegister.entities.foreach((entity) => entity.drawSprite(g))

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


    for (player <- EntityRegister.getPlayers) {
      val playerPosX = player.position.x
      val playerWidth = player.getCurrentSpriteFrame.getRegionWidth

      val bordersAdjustedX = math.max(0, math.min(getWindowWidth - (playerWidth.toFloat / 2f).toInt, playerPosX))

      player.position.x = bordersAdjustedX
    }
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
      scene.stopMusic()
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

  private def setupControllerListeners(): Unit = {
    val controllers = Controllers.getControllers

    // The implementation of the controller linking is not great
    // We really wanted to have controllers working for this project, so we simply tried to make it work
    // Don't be harsh with us about that please T_T
    controllers.forEach(controller => {
      var playerOption: Option[Player] = None
      controller.addListener(new ControllerListener {
        override def connected(controller: Controller): Unit = {}

        override def disconnected(controller: Controller): Unit = {}

        override def buttonDown(controller: Controller, i: Int): Boolean = {
          if (i == Xbox.START) {
            val freePlayer = controllerMap.keys.find((player) => controllerMap(player).isEmpty)
            println(freePlayer)
            if (freePlayer.isDefined && playerOption.isEmpty) {
              controllerMap(freePlayer.get) = Some(controller)
              playerOption = freePlayer
            }
            return false
          }

          if (playerOption.isDefined) {
            playerOption.get.handleControllerButtonDown(i)
          }
          true
        }

        override def buttonUp(controller: Controller, i: Int): Boolean = {
          if (playerOption.isDefined) {
            playerOption.get.handleControllerButtonUp(i)
          }
          false
        }

        override def axisMoved(controller: Controller, i: Int, v: Float): Boolean = {

          false
        }

        override def povMoved(controller: Controller, i: Int, povDirection: PovDirection): Boolean = {
          if (playerOption.isDefined) {
            playerOption.get.handleControllerPovDirectionChange(povDirection)
          }
          false
        }

        override def xSliderMoved(controller: Controller, i: Int, b: Boolean): Boolean = {

          false
        }

        override def ySliderMoved(controller: Controller, i: Int, b: Boolean): Boolean = {

          false
        }

        override def accelerometerMoved(controller: Controller, i: Int, vector3: Vector3): Boolean = false
      })
    })
  }
}
