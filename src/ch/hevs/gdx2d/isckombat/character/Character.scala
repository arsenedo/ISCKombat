package ch.hevs.gdx2d.isckombat.character

import ch.hevs.gdx2d.isckombat.sprites.{SpriteConfig, SpritesLoader}
import ch.hevs.gdx2d.isckombat.state.{IdleState, State}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import ch.hevs.gdx2d.isckombat.sprites.SpriteActions

abstract class Character(val position: Vector2) {
  loadSpritesheets()

  private var health: Int = 1000
  private var hitbox: Option[Hitbox] = None
  private var currentFrame = 0
  private var currentTick = 0
  private var currentSprite: SpriteConfig = _
  private var state: State = new IdleState
  state.enter(this)

  def updateSpritesheet(newSpritesheet: SpriteConfig): Unit = {
    currentTick = 0
    currentFrame = 0
    currentSprite = newSpritesheet
  }

  def updateState(newState: State) = {
    state.exit(this)

    newState.enter(this)

    state = newState
  }

  def update(enemyPos: Vector2): Unit = {
    currentTick += 1

    if (currentTick % currentSprite.ticksPerFrame == 0) {
      currentFrame += 1
    }

    state.update(this)

    flipSprites(enemyPos)
  }

  def handleKeyDown(keycode: Int): Unit = {
    state.handleKeyDown(keycode, this)
  }

  def handleKeyUp(keycode: Int): Unit = {
    state.handleKeyUp(keycode, this)
  }

  def activateHitbox(action: SpriteActions.Action): Unit = {
    val currentSpriteFrame = getCurrentSpriteFrame

    val hitboxOffset: Vector2 = {
      action match {
        case SpriteActions.ATTACK_TOP => new Vector2(0, currentSpriteFrame.getRegionHeight.toFloat * (2f/3f))
        case SpriteActions.ATTACK_MID => new Vector2(0, currentSpriteFrame.getRegionHeight.toFloat * (1f/3f))
        case SpriteActions.ATTACK_BOTTOM => new Vector2(0, 0)
        case _ => new Vector2(0, 0)
      }
    }

    hitboxOffset.add(getFlipAdjustedPosition)

    hitbox = Option(Hitbox(hitboxOffset, currentSpriteFrame.getRegionWidth, (currentSpriteFrame.getRegionHeight.toFloat * (1f/3f)).toInt))
  }

  def deactivateHitbox(): Unit = {
    hitbox = None
  }

  def drawSprite(g: GdxGraphics): Unit = {
    val flipAdjustedPos = getFlipAdjustedPosition
    g.draw(getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }

  def drawDebugBoxes(g: GdxGraphics): Unit = {
    val flipAdjustedPos = getFlipAdjustedPosition
    g.setColor(Color.LIME)

    g.drawRectangle(
      flipAdjustedPos.x + getCurrentSpriteFrame.getRegionWidth / 2,
      flipAdjustedPos.y + getCurrentSpriteFrame.getRegionHeight / 2,
      getCurrentSpriteFrame.getRegionWidth,
      getCurrentSpriteFrame.getRegionHeight,
      0
    )

    if (hitbox.isDefined) {
      val hb = hitbox.get
      val pos = hb.position
      g.setColor(Color.RED)
      g.drawRectangle(
        pos.x + hb.width / 2,
        pos.y + hb.height / 2,
        hb.width,
        hb.height,
        0
      )
    }
  }

  def getHitbox: Option[Hitbox] = hitbox

  def getSpritesLoader: SpritesLoader

  def getCurrentFrame: Int = currentFrame

  def getCurrentSpriteConfig: SpriteConfig = currentSprite

  def getFlipAdjustedPosition: Vector2 = {
    if (!getCurrentSpriteFrame.isFlipX) {
      return position
    }

    val idleFrameWidth: Int = getSpritesLoader.getIdleSpritesheet.spritesheet.sprites(0)(0).getRegionWidth
    val currentSpriteFrameWidth: Int = getCurrentSpriteFrame.getRegionWidth

    val dx = currentSpriteFrameWidth - idleFrameWidth

    new Vector2(position.x - dx, position.y)
  }

  def getCurrentSpriteFrame: TextureRegion = {
    currentSprite.spritesheet.sprites(0)(currentFrame % currentSprite.nFrames)
  }

  def getHealth: Int = {
    health
  }

  def applyDamage(damage: Int): Unit = {
    health -= damage
  }

  private def flipSprites(enemyPos: Vector2): Unit = {
    // IF ENEMY ON RIGHT (dist < 0), FLIPPED = FALSE
    // IF ENEMY ON LEFT (dist > 0), FLIPPED = TRUE
    val dist: Float = position.x - enemyPos.x

    if (
      (dist < 0 && getCurrentSpriteFrame.isFlipX)
      || (dist > 0 && !getCurrentSpriteFrame.isFlipX)
    ) {
      getCurrentSpriteFrame.flip(true, false)
    }
  }

  private def loadSpritesheets(): Unit = {
    getSpritesLoader.loadSpritesheets()
  }
}
