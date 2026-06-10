package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.entity.Hurtbox
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

object DioSpritesLoader extends SpritesLoader{
  private var spritesLoaded = false

  private var stanceSpritesheet: SpriteConfig = _

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "characters/dio"
  }

  override def soundsPath: String = {
    super.soundsPath + "characters/dio"
  }

  override def loadSpritesheets(): Unit = {
    if (spritesLoaded) {
      return
    }

    idleSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/idle.png", 209, 380),
      6,
      3,
    )

    walkSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/walk.png", 281, 375),
      16,
      4
    )

    punchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/punch.png", 375, 375),
      6,
      3,
      Some(HashMap(2->SpriteActions.ATTACK_TOP)),
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/khum.wav")
      ))
    )

    hitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hit.png", 399, 365),
      8,
      2,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/ugh.wav")
      ))
    )

    jumpSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/jump.png", 255, 390),
      5,
      3,
      hurtboxesOnFrames = Some(HashMap(
        1 -> Hurtbox(255, 253, 0, 137),
        2 -> Hurtbox(255, 253, 0, 137),
        3 -> Hurtbox(255, 253, 0, 137),
        4 -> Hurtbox(255, 253, 0, 137)
      ))
    )

    directionalJump = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/directional_jump.png", 232, 250),
      1,
      5
    )

    fallingSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/knockout.png", 470, 375),
      9,
      4,
      isOneShot = true,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/knockout.wav")
      ))
    )

    victorySpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/victory.png", 302, 375),
      14,
      6,
      isOneShot = true,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/victory.wav")
      ))
    )

    crouchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch.png", 243, 300),
      2,
      3,
      None,
      hurtboxesOnFrames = Some(HashMap(
        1 -> Hurtbox(284, 240, 0, 0),
      )),
      isOneShot = true
    )


    crouchKickSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_kick.png", 394, 230),
      7,
      3,
      actionsOnFrames = Some(HashMap(2->SpriteActions.ATTACK_BOTTOM)),
    )

    crouchHitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_hit.png", 380, 340),
      6,
      6,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/ugh.wav")
      ))
    )

    blockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/block.png", 237, 365),
      1,
      3,
      isOneShot = true
    )

    crouchBlockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_Block.png", 409, 350),
      3,
      3,
      isOneShot = true
    )

    stanceSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/stance.png", 299, 375),
      4,
      3,
      isOneShot = true
    )

    spritesLoaded = true
  }

  def getStanceSpritesheet: SpriteConfig = stanceSpritesheet

  override def getFlippableSpirtesheets: ArrayBuffer[SpriteConfig] = {
    val parentFlippables: ArrayBuffer[SpriteConfig] = super.getFlippableSpirtesheets

    parentFlippables.addOne(getStanceSpritesheet)

    parentFlippables
  }
}
