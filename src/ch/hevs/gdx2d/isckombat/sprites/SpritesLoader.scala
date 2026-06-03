package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

trait SpritesLoader {
  protected var idleSpritesheet: SpriteConfig = _
  protected var walkSpritesheet: SpriteConfig = _
  protected var punchSpritesheet: SpriteConfig = _
  protected var hitSpritesheet: SpriteConfig = _
  protected var jumpSpritesheet: SpriteConfig = _
  protected var directionalJump: SpriteConfig = _
  protected var fallingSpritesheet: SpriteConfig = _
  protected var victorySpritesheet: SpriteConfig = _
  protected var crouchSpritesheet: SpriteConfig = _
  protected var crouchKickSpritesheet: SpriteConfig = _
  protected var crouchHitSpritesheet: SpriteConfig = _
  protected var blockSpritesheet: SpriteConfig = _
  protected var crouchBlockSpritesheet: SpriteConfig = _

  def spritesheetsPath: String = "data/images/spritesheets/"
  def soundsPath: String = "data/sounds/"

  def loadSpritesheets(): Unit
  def getIdleSpritesheet: SpriteConfig = idleSpritesheet
  def getWalkSpritesheet: SpriteConfig = walkSpritesheet
  def getPunchSpritesheet: SpriteConfig = punchSpritesheet
  def getHitSpritesheet: SpriteConfig = hitSpritesheet
  def getJumpSpritesheet: SpriteConfig = jumpSpritesheet
  def getDirectionalJumpSpritesheet: SpriteConfig = directionalJump
  def getFallingSpritesheet: SpriteConfig = fallingSpritesheet
  def getVictorySpritesheet: SpriteConfig = victorySpritesheet
  def getCrouchSpritesheet: SpriteConfig = crouchSpritesheet
  def getCrouchkickSpritesheet: SpriteConfig = crouchKickSpritesheet
  def getCrouchHitSpritesheet: SpriteConfig = crouchHitSpritesheet
  def getBlockSpritesheet: SpriteConfig = blockSpritesheet
  def getCrouchBlockSpritesheet: SpriteConfig = crouchBlockSpritesheet


  def setAllSpritesFlipState(isFlipped: Boolean): Unit = {
    val spritesheets: Array[SpriteConfig] = Array(
      getIdleSpritesheet,
      getWalkSpritesheet,
      getPunchSpritesheet,
      getHitSpritesheet,
      getJumpSpritesheet,
      getFallingSpritesheet,
      getCrouchSpritesheet,
      getCrouchkickSpritesheet,
      getCrouchHitSpritesheet,
      getCrouchkickSpritesheet,
      getBlockSpritesheet,
      getCrouchBlockSpritesheet
    )

    for (spriteConf <- spritesheets) {
      setSpritesheetFlipState(spriteConf.spritesheet, isFlipped)
    }
  }

  def setSpritesheetFlipState(spritesheet: Spritesheet, shouldBeFlipped: Boolean): Unit = {
    for (row <- spritesheet.sprites) {
      for (sprite <- row) {
        if ((shouldBeFlipped && !sprite.isFlipX) || (!shouldBeFlipped && sprite.isFlipX)) {
          sprite.flip(true, false)
        }
      }
    }
  }
}
