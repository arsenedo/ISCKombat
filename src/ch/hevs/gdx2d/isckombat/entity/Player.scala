package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.{Controllable, InputCommand}
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.state.{IdleState, State}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

abstract class Player(id: Int, position: Vector2) extends Entity(id: Int, position: Vector2) with Controllable {
  private var state: State[Player] = new IdleState
  private var health: Int = 1000
  private val hitboxManager: HitboxManager = new HitboxManager(id, () => {getFlipAdjustedPosition})

  state.enter(this)

  def updateState(newState: State[Player]): Unit = {
    state.exit(this)
    hitboxManager.clearHitboxesMap()

    newState.enter(this)
    hitboxManager.updateHitboxesMap(getCurrentSpriteConfig)

    state = newState
  }

  def update(enemyPos: Vector2): Unit = {
    super.update()

    state.update(this)

    flipSprites(enemyPos)
  }

  def getLatestCommand: Option[InputCommand] = {
    inputsHandler.getHistory.lastOption
  }

  def isToggled(action: InputAction): Boolean = {
    inputsHandler.isToggled(action)
  }

  def getHitboxAtCurrentFrame: Option[Hitbox] = {
    hitboxManager.getHitboxAtFrame(getCurrentFrame)
  }

  def getHealth: Int = {
    health
  }

  def applyDamage(damage: Int): Unit = {
    health -= damage
    println(health)
  }

  override def drawSprite(g: GdxGraphics): Unit = {
    state.drawSprite(g, this)
  }

  override def drawDebugBoxes(g: GdxGraphics): Unit = {
    super.drawDebugBoxes(g)

    val hbOption = getHitboxAtCurrentFrame
    if (hbOption.isDefined) {
      val hitbox = hbOption.get
      val pos = hitbox.position
      g.setColor(Color.RED)
      g.drawRectangle(
        pos.x + hitbox.width / 2,
        pos.y + hitbox.height / 2,
        hitbox.width,
        hitbox.height,
        0
      )
    }
  }
}
