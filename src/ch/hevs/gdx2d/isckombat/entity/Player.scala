package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.{Controllable, InputCommand}
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.state.playerStates.{IdleState, PlayerState}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.immutable.HashMap

abstract class Player(id: Int, position: Vector2) extends Entity(id: Int, position: Vector2) with Controllable {
  var enemyId = -1

  private var state: PlayerState = new IdleState
  private var health: Int = 1000

  state.enter(this)

  def getCombinations: HashMap[String, Array[InputAction]] = HashMap.empty

  def getCombinationStates: HashMap[String, PlayerState] = HashMap.empty

  def updateState(newState: PlayerState): Unit = {
    state.exit(this)

    newState.enter(this)

    state = newState
  }

  def update(enemyPos: Vector2): Unit = {
    super.update()

    state.checkActiveCombo(this)

    state.update(this)

    flipSprites(enemyPos)
  }

  def tryExecuteLastCommand(inputAction: InputAction, onSuccess: () => Unit): Boolean = {
    val latestCommandOption = inputsHandler.getHistory.lastOption
    if (latestCommandOption.isEmpty) return false
    val latestCommand = latestCommandOption.get
    if (latestCommand.isActive && !latestCommand.hasActivatedACombo && latestCommand.action == inputAction) {
      onSuccess()
      return true
    }

    false
  }

  def tryPlaySoundOnCurrentFrame(): Unit = {
    if (getCurrentSpriteConfig.soundOnFrames.isDefined) {
      val soundOnFrames = getCurrentSpriteConfig.soundOnFrames.get

      val moduloFrame = getCurrentFrame % getCurrentSpriteConfig.nFrames
      if (soundOnFrames.contains(moduloFrame)) {
        soundOnFrames(moduloFrame).stop()
        soundOnFrames(moduloFrame).play()
      }
    }
  }

  def isToggled(action: InputAction): Boolean = {
    inputsHandler.isToggled(action)
  }

  def getHurtboxAtCurrentFrame: Hurtbox = {
    getCurrentSpriteConfig.getHurtboxOnFrame(getCurrentFrame)
  }

  def getHealth: Int = {
    health
  }

  override def getFlipAdjustedPosition: Vector2 = {
    if (!getCurrentSpriteFrame.isFlipX) {
      return new Vector2(position.x, position.y)
    }

    val idleFrameWidth: Int = getSpritesLoader.getIdleSpritesheet.spritesheet.sprites(0)(0).getRegionWidth
    val currentSpriteFrameWidth: Int = getCurrentSpriteFrame.getRegionWidth

    val dx = currentSpriteFrameWidth - idleFrameWidth

    new Vector2(position.x - dx, position.y)
  }

  def applyDamage(damage: Int): Unit = {
    health -= damage
    println(health)
  }

  def receiveDamage(damage: Int): Unit = {
    state.receiveDamage(this, damage)
  }

  override def drawSprite(g: GdxGraphics): Unit = {
    state.drawSprite(g, this)
  }

  override def drawDebugBoxes(g: GdxGraphics): Unit = {
    super.drawDebugBoxes(g)

    val flipAdjustedPos: Vector2 = getFlipAdjustedPosition
    val hurtbox = getHurtboxAtCurrentFrame
    g.setColor(Color.LIME)
    g.drawRectangle(
      flipAdjustedPos.x + hurtbox.width / 2,
      flipAdjustedPos.y + hurtbox.height / 2,
      hurtbox.width,
      hurtbox.height,
      0
    )
  }
}
