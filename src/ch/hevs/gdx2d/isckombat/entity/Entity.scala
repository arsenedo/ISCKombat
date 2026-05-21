package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.{SpriteConfig, SpritesLoader}
import ch.hevs.gdx2d.isckombat.state.{IdleState, State}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

abstract class Entity(val id: Int, val position: Vector2) {
  loadSpritesheets()

  private var health: Int = 1000
  private var currentFrame = 0
  private var currentTick = 0
  private var currentSprite: SpriteConfig = _
  private var state: State = new IdleState
  private val hitboxManager: HitboxManager = new HitboxManager(id, () => {getFlipAdjustedPosition})
  state.enter(this)

  def updateSpritesheet(newSpritesheet: SpriteConfig): Unit = {
    currentTick = 0
    currentFrame = 0
    currentSprite = newSpritesheet
  }

  def updateState(newState: State) = {
    state.exit(this)
    hitboxManager.clearHitboxesMap()

    newState.enter(this)
    hitboxManager.updateHitboxesMap(getCurrentSpriteConfig)

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

  def getHitboxAtCurrentFrame: Option[Hitbox] = {
    hitboxManager.getHitboxAtFrame(currentFrame)
  }

  def getSpritesLoader: SpritesLoader

  def getCurrentFrame: Int = currentFrame

  def getCurrentSpriteConfig: SpriteConfig = currentSprite

  private def getFlipAdjustedPosition: Vector2 = {
    if (!getCurrentSpriteFrame.isFlipX) {
      return new Vector2(position.x, position.y)
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
    println(health)
  }

  private def flipSprites(enemyPos: Vector2): Unit = {
    // IF ENEMY ON RIGHT (dist < 0), FLIPPED = FALSE
    // IF ENEMY ON LEFT (dist > 0), FLIPPED = TRUE
    val dist: Float = position.x - enemyPos.x

    getSpritesLoader.setAllSpritesFlipState(dist > 0)
  }

  private def loadSpritesheets(): Unit = {
    getSpritesLoader.loadSpritesheets()
  }
}
