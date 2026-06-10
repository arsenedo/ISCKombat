package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

object TheWorldSpritesLoader extends SpritesLoader {
  private var flySpritesheet: SpriteConfig = _
  private var attackSpritesheet: SpriteConfig = _

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "characters/dio/stand"
  }

  override def loadSpritesheets(): Unit = {
    flySpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/stand_fly.png", 240, 380),
      4,
      5
    )

    attackSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/stand_attack.png", 417, 370),
      38,
      4,
      actionsOnFrames = Some(HashMap(
        4 -> SpriteActions.ATTACK_TOP,
        8 -> SpriteActions.ATTACK_TOP,
        12 -> SpriteActions.ATTACK_TOP,
        16 -> SpriteActions.ATTACK_TOP,
        20 -> SpriteActions.ATTACK_TOP,
        24 -> SpriteActions.ATTACK_TOP,
        28 -> SpriteActions.ATTACK_TOP,
        32 -> SpriteActions.ATTACK_TOP,
      )),
      isOneShot = true
    )
  }

  def getFlySpritesheet: SpriteConfig = flySpritesheet

  def getAttackSpritesheet: SpriteConfig = attackSpritesheet

  override def getFlippableSpirtesheets: ArrayBuffer[SpriteConfig] = {
    ArrayBuffer[SpriteConfig](
      getFlySpritesheet,
      getAttackSpritesheet
    )
  }
}
