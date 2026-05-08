package ch.hevs.gdx2d.isckombat.character

import ch.hevs.gdx2d.isckombat.sprites.{ScorpionSpritesLoader, SpriteConfig}
import com.badlogic.gdx.math.Vector2

class Scorpion(position: Vector2) extends Character(position) {
  currentSprite = getIdleSpritesheet

  override def loadSpritesheets(): Unit = {
    ScorpionSpritesLoader.loadSpritesheets()
  }

  override def getIdleSpritesheet: SpriteConfig = {
    ScorpionSpritesLoader.getIdleSpritesheet
  }

  override def getWalkSpritesheet: SpriteConfig = {
    ScorpionSpritesLoader.getWalkSpritesheet
  }
}
