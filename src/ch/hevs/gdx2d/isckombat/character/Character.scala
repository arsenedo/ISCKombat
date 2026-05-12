package ch.hevs.gdx2d.isckombat.character

import ch.hevs.gdx2d.isckombat.sprites.{SpriteConfig, SpritesLoader}
import ch.hevs.gdx2d.isckombat.state.{IdleState, State}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

abstract class Character(val position: Vector2) {
  loadSpritesheets()

  private var currentSprite: SpriteConfig = getSpritesLoader.getIdleSpritesheet
  private var state: State = new IdleState
  private var currentFrame = 0
  private var currentTick = 0

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

  def drawSprite(g: GdxGraphics): Unit = {
    val flipAdjustedPos = getFlipAdjustedPosition
    g.draw(getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }

  def drawDebugBox(g: GdxGraphics): Unit = {
    val flipAdjustedPos = getFlipAdjustedPosition
    g.setColor(Color.LIME)

    g.drawRectangle(
      flipAdjustedPos.x + getCurrentSpriteFrame.getRegionWidth / 2,
      flipAdjustedPos.y + getCurrentSpriteFrame.getRegionHeight / 2,
      getCurrentSpriteFrame.getRegionWidth,
      getCurrentSpriteFrame.getRegionHeight,
      0
    )
  }

  def getSpritesLoader: SpritesLoader

  def getCurrentFrame: Int = currentFrame

  def getCurrentSpriteConfig: SpriteConfig = currentSprite

  private def getFlipAdjustedPosition: Vector2 = {
    if (!getCurrentSpriteFrame.isFlipX) {
      return position
    }

    val idleFrameWidth: Int = getSpritesLoader.getIdleSpritesheet.spritesheet.sprites(0)(0).getRegionWidth
    val currentSpriteFrameWidth: Int = getCurrentSpriteFrame.getRegionWidth

    val dx = currentSpriteFrameWidth - idleFrameWidth

    new Vector2(position.x - dx, position.y)
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

  private def getCurrentSpriteFrame: TextureRegion = {
    currentSprite.spritesheet.sprites(0)(currentFrame % currentSprite.nFrames)
  }
}
