package ch.hevs.gdx2d.isckombat.character

import ch.hevs.gdx2d.isckombat.sprites.{SpriteConfig, SpritesLoader}
import ch.hevs.gdx2d.isckombat.state.{IdleState, State}
import ch.hevs.gdx2d.lib.GdxGraphics
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

  def update(): Unit = {
    currentTick += 1

    state.update(this)
  }

  def handleKeyDown(keycode: Int): Unit = {
    state.handleKeyDown(keycode, this)
  }

  def handleKeyUp(keycode: Int): Unit = {
    state.handleKeyUp(keycode, this)
  }

  def drawSprite(g: GdxGraphics): Unit = {
    if (currentTick % currentSprite.ticksPerFrame == 0) {
      currentFrame += 1
    }

    g.draw(currentSprite.spritesheet.sprites(0)(currentFrame % currentSprite.nFrames), position.x, position.y)
  }

  def getSpritesLoader: SpritesLoader

  def getCurrentFrame: Int = currentFrame

  def getCurrentSpriteConfig: SpriteConfig = currentSprite

  private def loadSpritesheets(): Unit = {
    getSpritesLoader.loadSpritesheets()
  }
}
