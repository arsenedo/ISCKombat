package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.{SpriteConfig, SpritesLoader}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

abstract class Entity(val id: Int, val position: Vector2) {
  loadSpritesheets()

  private var currentFrame = 0
  private var currentTick = 0
  private var currentSprite: SpriteConfig = _

  def updateSpritesheet(newSpritesheet: SpriteConfig): Unit = {
    currentTick = 0
    currentFrame = 0
    currentSprite = newSpritesheet
  }

  def update(): Unit = {
    currentTick += 1

    if (currentTick % currentSprite.ticksPerFrame == 0) {
      currentFrame += 1
    }

    val currentSpritesConfig: SpriteConfig = getCurrentSpriteConfig

    if ((currentSpritesConfig.isOneShot) && (currentFrame >= currentSpritesConfig.nFrames - 1)) {
      currentFrame = currentSpritesConfig.nFrames - 1
    }
  }

  def drawSprite(g: GdxGraphics): Unit = {
    val flipAdjustedPos = this.getFlipAdjustedPosition
    g.draw(this.getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }

  def drawDebugBoxes(g: GdxGraphics): Unit = {}

  def getSpritesLoader: SpritesLoader

  def getCurrentFrame: Int = currentFrame

  def getCurrentTick: Int = currentTick

  def getCurrentSpriteConfig: SpriteConfig = currentSprite

  def getFlipAdjustedPosition: Vector2 = {
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

  protected def flipSprites(enemyPos: Vector2): Unit = {
    // IF ENEMY ON RIGHT (dist < 0), FLIPPED = FALSE
    // IF ENEMY ON LEFT (dist > 0), FLIPPED = TRUE
    val dist: Float = position.x - enemyPos.x

    getSpritesLoader.setAllSpritesFlipState(dist > 0)
  }

  private def loadSpritesheets(): Unit = {
    getSpritesLoader.loadSpritesheets()
  }
}
