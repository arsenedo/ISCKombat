package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.entity.Hurtbox
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object MichaelJacksonSpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  private var victorySpritesheetArray: Array[SpriteConfig] = Array.empty

  private var hatThrowSpritesheet: SpriteConfig = _

  override def spritesheetsPath: String = {
    super.spritesheetsPath + "characters/mj"
  }

  override def soundsPath: String = {
    super.soundsPath + "characters/mj"
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
      Some(HashMap(2->SpriteActions.ATTACK_TOP)),
    )

    jumpSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/jump.png", 167, 350),
      1,
      1,
    )

    directionalJump = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/directional_jump.png", 187, 350),
      2,
      10,
    )

    hitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hit.png", 193, 373),
      3,
      6,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/dah.wav")
      ))
    )

    fallingSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/knockout.png", 412, 373),
      5,
      6,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/daaw.wav")
      )),
    )

    victorySpritesheetArray = Array(
      SpriteConfig(
        new Spritesheet(s"$spritesheetsPath/victory1.png", 275, 373),
        24,
        12,
        soundOnFrames = Some(HashMap(
          0 -> new SoundSample(s"$soundsPath/victory1.mp3")
        ))
      ),
      SpriteConfig(
        new Spritesheet(s"$spritesheetsPath/victory2.png", 402, 373),
        31,
        12,
        soundOnFrames = Some(HashMap(
          0 -> new SoundSample(s"$soundsPath/victory2.mp3")
        ))
      )
    )

    crouchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch.png", 154, 330),
      2,
      3,
      hurtboxesOnFrames = Some(HashMap(
        1 -> Hurtbox(154, 205, 0, 0)
      )),
      isOneShot = true
    )

    crouchKickSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_punch.png", 216, 210),
      3,
      10,
      Some(HashMap(2->SpriteActions.ATTACK_TOP)),
    )

    crouchHitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_hit.png", 139, 220),
      1,
      30
    )

    blockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/block.png", 165, 375),
      6,
      2
    )

    crouchBlockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_block.png", 168, 220),
      1,
      1
    )

    hatThrowSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hat_throw.png", 236, 373),
      4,
      5,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/smooth_criminal.wav")
      ))
    )

    spritesLoaded = true
  }

  def getHatThrowSpritesheet: SpriteConfig = hatThrowSpritesheet

  override def getVictorySpritesheet: SpriteConfig = {
    victorySpritesheet = victorySpritesheetArray(Random.between(0, victorySpritesheetArray.length))

    super.getVictorySpritesheet
  }

  override def getFlippableSpirtesheets: ArrayBuffer[SpriteConfig] = {
    val parentFlippables: ArrayBuffer[SpriteConfig] = super.getFlippableSpirtesheets

    parentFlippables.addOne(getHatThrowSpritesheet)

    parentFlippables
  }
}
