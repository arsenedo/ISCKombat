package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

object SpearSpritesLoader extends SpritesLoader {
  private var thrownSpearSpritesheet: SpriteConfig = _

  private var caughtSpearSpritesheet: SpriteConfig = _

  def getThrownSpearSpritesheet: SpriteConfig = thrownSpearSpritesheet

  def getCaughtSpearSpritesheet: SpriteConfig = caughtSpearSpritesheet

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "items/spear"
  }

  override def loadSpritesheets(): Unit = {
    thrownSpearSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/thrown.png", 733, 75),
      3,
      5,
      Some(HashMap(0 -> SpriteActions.ATTACK_MID, 1 -> SpriteActions.ATTACK_MID, 2 -> SpriteActions.ATTACK_MID))
    )

    caughtSpearSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/caught.png", 700, 21),
      1,
      1
    )
  }

  override def getFlippableSpirtesheets: ArrayBuffer[SpriteConfig] = {
    ArrayBuffer[SpriteConfig](
      getThrownSpearSpritesheet,
      getCaughtSpearSpritesheet
    )
  }
}
