package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

object ScorpionSpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  override def spritesheetsPath: String = {
    "data/images/spritesheets/characters/scorpion"
  }

  override def loadSpritesheets(): Unit = {
    if (spritesLoaded) {
      return
    }

    idleSpritesheet = SpriteConfig(
      new Spritesheet(s"${spritesheetsPath()}/idle.png", 76, 134),
      7,
      6
    )

    walkSpritesheet = SpriteConfig(
      new Spritesheet(s"${spritesheetsPath()}/walk.png", 87, 136),
      9,
      6
    )

    spritesLoaded = true
  }
}
