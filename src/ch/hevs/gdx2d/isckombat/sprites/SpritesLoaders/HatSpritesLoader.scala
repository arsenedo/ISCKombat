package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap

object HatSpritesLoader extends SpritesLoader {
  var hatSpritesheet: SpriteConfig = _

  def getHatSpritesheet: SpriteConfig = hatSpritesheet

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "items"
  }

  override def loadSpritesheets(): Unit = {
    hatSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hat.png", 71, 50),
      1,
      1,
      actionsOnFrames = Option(HashMap(0 -> SpriteActions.ATTACK_FULL))
    )
  }
}
