package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.collection.immutable.HashMap

object MichaelJacksonSpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "characters/mj"
  }

  override def loadSpritesheets(): Unit = {
    if (spritesLoaded) {
      return
    }

    idleSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/idle.png", 116, 376),
      1,
      1
    )

    walkSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/walk.png", 186, 385),
      7,
      7
    )

    punchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/punch.png", 241, 376),
      5,
      6,
      Some(HashMap(2->SpriteActions.ATTACK_TOP))
    )

    hitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hit.png", 193, 373),
      3,
      6
    )

    spritesLoaded = true
  }
}
