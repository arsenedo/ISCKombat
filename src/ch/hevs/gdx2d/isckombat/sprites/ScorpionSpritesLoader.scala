package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import scala.collection.immutable.HashMap

object ScorpionSpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "characters/scorpion"
  }

  override def loadSpritesheets(): Unit = {
    if (spritesLoaded) {
      return
    }

    idleSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/idle.png", 213, 375),
      7,
      6,
    )

    walkSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/walk.png", 243, 380),
      9,
      6
    )

    punchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/punch.png", 330, 386),
      6,
      6,
      Some(HashMap(2->SpriteActions.ATTACK_TOP, 5->SpriteActions.ATTACK_TOP))
    )

    hitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hit.png", 238, 380),
      3,
      6
    )

    knockoutSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/knockout.png", 360, 370),
      5,
      6
    )

    victorySpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/victory.png", 187, 420),
      4,
      5
    )

    spritesLoaded = true
  }
}
