package ch.hevs.gdx2d.isckombat

import ch.hevs.gdx2d.desktop.{PortableApplication, Xbox}
import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.state.gameState.{GameState, MenuState}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.controllers.{Controller, ControllerListener, Controllers, PovDirection}
import com.badlogic.gdx.math.Vector3

import scala.collection.mutable

object Game {
  def main(args: Array[String]): Unit = {
    new Game
  }
}

class Game extends PortableApplication(1920, 1080){
  val controllersMap: mutable.HashMap[Controller, String] = mutable.HashMap.empty
  val P1Key = "p1"
  val P2Key = "p2"

  private var gameState: GameState = new MenuState

  override def onInit(): Unit = {
    setupControllerListeners()
    gameState.enter(this)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    gameState.update(this)

    g.clear()

    gameState.onGraphicsRender(g, this)
  }

  def updateGameState(newState: GameState): Unit = {
    gameState.exit(this)

    gameState = newState

    newState.enter(this)
  }

  override def onKeyDown(keycode: Int): Unit = {
    gameState.onHandleKeyDown(keycode)
  }

  override def onKeyUp(keycode: Int): Unit = {
    gameState.onHandleKeyUp(keycode)
  }


  private def setupControllerListeners(): Unit = {
    val controllers = Controllers.getControllers

    // The implementation of the controller linking is not great
    // We really wanted to have controllers working for this project, so we simply tried to make it work
    // Don't be harsh with us about that please T_T
    controllers.forEach(controller => {
      val controllerListener = new ControllerListener {
        override def connected(controller: Controller): Unit = {}

        override def disconnected(controller: Controller): Unit = {}

        override def buttonDown(controller: Controller, i: Int): Boolean = {
          if (i == Xbox.START) {
            if (!controllersMap.contains(controller)) {
              // New controller! Check which player is available
              if (!controllersMap.values.toArray.contains(P1Key)) {
                controllersMap.addOne(controller, P1Key)
                return true
              } else if (!controllersMap.values.toArray.contains(P2Key)) {
                controllersMap.addOne(controller, P2Key)
                return true
              }
            }

            return false
          }

          handleControllerButtonDown(controller, i)
          true
        }

        override def buttonUp(controller: Controller, i: Int): Boolean = {
          handleControllerButtonUp(controller, i)
          false
        }

        override def axisMoved(controller: Controller, i: Int, v: Float): Boolean = {

          false
        }

        override def povMoved(controller: Controller, i: Int, povDirection: PovDirection): Boolean = {
          handleControllerPovMoved(controller, povDirection)
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
    })
  }

  private def handleControllerButtonDown(controller: Controller, i: Int): Unit = {
    if (controllersMap.contains(controller)) {
      gameState.handleControllerButtonDown(this, controller, i)
    }
  }

  private def handleControllerButtonUp(controller: Controller, i: Int): Unit = {
    if (controllersMap.contains(controller)) {
      gameState.handleControllerButtonUp(this, controller, i)
    }
  }

  private def handleControllerPovMoved(controller: Controller, povDirection: PovDirection): Unit = {
    if (controllersMap.contains(controller)) {
      gameState.handleControllerPovMoved(this, controller, povDirection)
    }
  }
}
