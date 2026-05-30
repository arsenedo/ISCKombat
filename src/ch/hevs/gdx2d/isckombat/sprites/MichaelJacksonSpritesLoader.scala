package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.collection.immutable.HashMap
import scala.util.Random

object MichaelJacksonSpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  private var victorySpritesheetArray: Array[SpriteConfig] = Array.empty

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

    knockoutSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/knockout.png", 412, 373),
      5,
      6
    )

    victorySpritesheetArray = Array(
      SpriteConfig(
        new Spritesheet(s"$spritesheetsPath/victory1.png", 275, 373),
        24,
        12
      ),
      SpriteConfig(
        new Spritesheet(s"$spritesheetsPath/victory2.png", 402, 373),
        31,
        12
      )
    )

    spritesLoaded = true
  }

  override def getVictorySpritesheet: SpriteConfig = {
    victorySpritesheet = victorySpritesheetArray(Random.between(0, victorySpritesheetArray.length))

    super.getVictorySpritesheet
  }
}
