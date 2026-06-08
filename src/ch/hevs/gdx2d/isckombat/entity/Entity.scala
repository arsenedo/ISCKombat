package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.sprites.{SpriteConfig, SpritesLoader}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

abstract class Entity(val id: Int, val position: Vector2) {
  loadSpritesheets()

  var currentFrame = 0

  protected val hitboxManager: HitboxManager = new HitboxManager(id, () => {getFlipAdjustedPosition})

  private var currentTick = 0
  private var currentSprite: SpriteConfig = _

  def onDelete(): Unit = {
    EntityRegister.removeEntity(this)
  }

  def updateSpritesheet(newSpritesheet: SpriteConfig): Unit = {
    currentTick = 0
    currentFrame = 0
    hitboxManager.clearHitboxesMap()
    currentSprite = newSpritesheet
    hitboxManager.updateHitboxesMap(getCurrentSpriteConfig)
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

  def drawDebugBoxes(g: GdxGraphics): Unit = {
    val hbOption = getHitboxAtCurrentFrame
    if (hbOption.isDefined) {
      val hitbox = hbOption.get
      val pos = hitbox.getGlobalPosition
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

  def getSpritesLoader: SpritesLoader

  def getCurrentFrame: Int = currentFrame

  def getCurrentTick: Int = currentTick

  def getCurrentSpriteConfig: SpriteConfig = currentSprite

  def getFlipAdjustedPosition: Vector2

  def getHitboxAtCurrentFrame: Option[Hitbox] = {
    hitboxManager.getHitboxAtFrame(getCurrentFrame % currentSprite.nFrames)
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
