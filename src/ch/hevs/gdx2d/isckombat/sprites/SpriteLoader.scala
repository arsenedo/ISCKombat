package ch.hevs.gdx2d.isckombat.sprites

trait SpritesLoader {
  protected var idleSpritesheet: SpriteConfig = _
  protected var walkSpritesheet: SpriteConfig = _

  def spritesheetsPath(): String

  def loadSpritesheets(): Unit
  def getIdleSpritesheet: SpriteConfig = idleSpritesheet
  def getWalkSpritesheet: SpriteConfig = walkSpritesheet
}
