package ch.hevs.gdx2d.isckombat.state.gameState

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.isckombat.Game
import ch.hevs.gdx2d.isckombat.collision.CollisionHandler
import ch.hevs.gdx2d.isckombat.entity.inputs.InputConfigs
import ch.hevs.gdx2d.isckombat.entity.{Entity, Hitbox, Player}
import ch.hevs.gdx2d.isckombat.interface.{Scene, StagesLoader}
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.state.playerStates.{KnockoutState, VictoryState}
import ch.hevs.gdx2d.isckombat.utils.GameMath
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.controllers.{Controller, ControllerListener, Controllers, PovDirection}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Vector2, Vector3}

import scala.collection.mutable

class MatchState(player1: Player, player2: Player) extends GameState {
  private val DEBUG_MODE: Boolean = false
  private var gameEnded: Boolean = false
  private var gameEndTimeS: Double = 0
  private var backToMenuTimerS: Long = 10
  private var scene : Scene = _

  private var controllerMap: mutable.LinkedHashMap[Player, Option[Controller]] = _

  private val controllersAndListenersMap: mutable.HashMap[Controller, ControllerListener] = mutable.HashMap()

  private var WINDOW_WIDTH = 0

  override def onGraphicsRender(g: GdxGraphics, game: Game): Unit = {
    scene.renderScene(g, player1, player2)

    EntityRegister.entities.foreach((entity) => entity.drawSprite(g))

    if (DEBUG_MODE) {
      drawDebugBoxes(g)
      g.drawFPS(Color.BLUE)
    }

  }

  override def onHandleKeyDown(key: Int): Unit = {
    player1.handleKeyDown(key)
    player2.handleKeyDown(key)
  }

  override def onHandleKeyUp(key: Int): Unit = {
    player1.handleKeyUp(key)
    player2.handleKeyUp(key)
  }

  override def enter(owner: Game): Unit = {
    WINDOW_WIDTH = owner.getWindowWidth

    scene = new Scene(StagesLoader.createRandomStage)
    player1.position.set(new Vector2(50,100))
    player2.position.set(new Vector2(owner.getWindowWidth - 200, 100))

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

  override def update(owner: Game): Unit = {
    simulationPhase()

    detectAndApplyCollisions()

    detectGameEnd(owner)
  }

  override def exit(owner: Game): Unit = {
    // Clean up controller listeners so that they can be set up again in the next match
    controllersAndListenersMap.foreachEntry((controller, controlerListener) => {
      controller.removeListener(controlerListener)
    })

    EntityRegister.removeEntity(player1)
    EntityRegister.removeEntity(player2)
  }

  private def simulationPhase(): Unit = {
    player1.update(player2.position)
    player2.update(player1.position)

    EntityRegister.entities.filter(entity => !entity.isInstanceOf[Player]).foreach(entity => entity.update())


    for (player <- EntityRegister.getPlayers) {
      val playerPosX = player.position.x
      val playerWidth = player.getCurrentSpriteFrame.getRegionWidth

      val bordersAdjustedX = math.max(0, math.min(WINDOW_WIDTH - (playerWidth.toFloat / 2f).toInt, playerPosX))

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

  private def detectGameEnd(game: Game): Unit = {
    if (gameEnded) {
      if (gameEndTimeS + backToMenuTimerS <= GameMath.nanoToS(System.nanoTime())) {
        game.updateGameState(new MenuState)
      }
      return
    }
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
      gameEndTimeS = GameMath.nanoToS(System.nanoTime())
    }
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
      val controllerListener = new ControllerListener {
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
      }
      controller.addListener(controllerListener)

      controllersAndListenersMap(controller) = controllerListener
    })
  }
}
