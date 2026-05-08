package ch.hevs.gdx2d.isckombat.character

import ch.hevs.gdx2d.isckombat.sprites.SpriteConfig
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

abstract class Character(position: Vector2) {
  var currentSprite: SpriteConfig = _

  private var currentFrame = 0
  private var currentTick = 0

  loadSpritesheets()

  def handleInput(keycode: Int): Unit ={

  }

  def drawSprite(g: GdxGraphics): Unit = {
    currentTick += 1
    if (currentTick == currentSprite.ticksPerFrame) {
      currentFrame = (currentFrame + 1) % currentSprite.nFrames
      currentTick = 0
    }

    g.draw(currentSprite.spritesheet.sprites(0)(currentFrame), position.x, position.y)
  }

  def loadSpritesheets(): Unit
  def getIdleSpritesheet: SpriteConfig
  def getWalkSpritesheet: SpriteConfig
}
