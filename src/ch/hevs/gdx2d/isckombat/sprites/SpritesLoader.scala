package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

trait SpritesLoader {
  protected var idleSpritesheet: SpriteConfig = _
  protected var walkSpritesheet: SpriteConfig = _
  protected var punchSpritesheet: SpriteConfig = _
  protected var hitSpritesheet: SpriteConfig = _
  protected var knockoutSpritesheet: SpriteConfig = _
  protected var victorySpritesheet: SpriteConfig = _

  def spritesheetsPath: String = "data/images/spritesheets/"

  def loadSpritesheets(): Unit
  def getIdleSpritesheet: SpriteConfig = idleSpritesheet
  def getWalkSpritesheet: SpriteConfig = walkSpritesheet
  def getPunchSpritesheet: SpriteConfig = punchSpritesheet
  def getHitSpritesheet: SpriteConfig = hitSpritesheet
  def getKnockoutSpritesheet: SpriteConfig = knockoutSpritesheet
  def getVictorySpritesheet: SpriteConfig = victorySpritesheet


  def setAllSpritesFlipState(isFlipped: Boolean): Unit = {
    val spritesheets: Array[SpriteConfig] = Array(
      getIdleSpritesheet,
      getWalkSpritesheet,
      getPunchSpritesheet,
      getHitSpritesheet,
      getKnockoutSpritesheet
    )

    for (spriteConf <- spritesheets) {
      setSpritesheetFlipState(spriteConf.spritesheet, isFlipped)
    }
  }

  private def setSpritesheetFlipState(spritesheet: Spritesheet, shouldBeFlipped: Boolean): Unit = {
    for (row <- spritesheet.sprites) {
      for (sprite <- row) {
        if ((shouldBeFlipped && !sprite.isFlipX) || (!shouldBeFlipped && sprite.isFlipX)) {
          sprite.flip(true, false)
        }
      }
    }
  }
}
