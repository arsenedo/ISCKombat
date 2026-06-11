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

    new SoundSample("data/sounds/misc/round1_fight.wav").play()

    scene.startMusic()
  }

  override def update(owner: Game): Unit = {
    simulationPhase()

    detectAndApplyCollisions()

    detectGameEnd(owner)
  }

  override def exit(owner: Game): Unit = {
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

  override def handleControllerButtonDown(game: Game, controller: Controller, i: Int): Unit = {
    if (game.controllersMap(controller) == game.P1Key) {
      player1.handleControllerButtonDown(i)
    } else if (game.controllersMap(controller) == game.P2Key) {
      player2.handleControllerButtonDown(i)
    }
  }

  override def handleControllerButtonUp(game: Game, controller: Controller, i: Int): Unit = {
    if (game.controllersMap(controller) == game.P1Key) {
      player1.handleControllerButtonUp(i)
    } else if (game.controllersMap(controller) == game.P2Key) {
      player2.handleControllerButtonUp(i)
    }
  }

  override def handleControllerPovMoved(game: Game, controller: Controller, povDirection: PovDirection): Unit = {
    if (game.controllersMap(controller) == game.P1Key) {
      player1.handleControllerPovDirectionChange(povDirection)
    } else if (game.controllersMap(controller) == game.P2Key) {
      player2.handleControllerPovDirectionChange(povDirection)
    }
  }
}
