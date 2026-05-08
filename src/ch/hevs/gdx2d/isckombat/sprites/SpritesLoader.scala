package ch.hevs.gdx2d.isckombat.sprites

trait SpritesLoader {
  protected var idleSpritesheet: SpriteConfig = _
  protected var walkSpritesheet: SpriteConfig = _
  protected var punchSpritesheet: SpriteConfig = _

  def spritesheetsPath: String = "data/images/spritesheets/"

  def loadSpritesheets(): Unit
  def getIdleSpritesheet: SpriteConfig = idleSpritesheet
  def getWalkSpritesheet: SpriteConfig = walkSpritesheet
  def getPunchSpritesheet: SpriteConfig = punchSpritesheet
}
