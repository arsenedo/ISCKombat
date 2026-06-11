package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.entity.Hurtbox
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.MichaelJacksonSpritesLoader.soundsPath
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer


object JohnnySpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  private var dashSpritesheet : SpriteConfig = _


  override def spritesheetsPath: String = {
    super.spritesheetsPath + "characters/johnny"
  }

  override def loadSpritesheets(): Unit = {
    if (spritesLoaded) {
      return
    }

    idleSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/idle.png", 243, 375),
      7,
      5,
    )

    walkSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/walk.png", 282, 375),
      5,
      6
    )

    punchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/punch.png", 372, 386),
      5,
      6,
      Some(HashMap(4->SpriteActions.ATTACK_TOP))
    )

    hitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/hit.png", 284, 380),
      4,
      6
    )

    jumpSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/jump.png", 180, 325),
      1,
      1
    )

    directionalJump = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/directional_jump.png", 205, 200),
      7,
      5
    )

    fallingSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/knockout.png", 320, 370),
      6,
      6
    )

    victorySpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/victory.png", 234, 420),
      12,
      5,
      soundOnFrames = Some(HashMap(
        0 -> new SoundSample(s"$soundsPath/characters/johnny/victory_troll.wav")
      ))
    )

    crouchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch.png", 245, 330),
      3,
      2,
      None,
      hurtboxesOnFrames = Some(HashMap(
        1 -> Hurtbox(195, 240, 0, 0),
        2 -> Hurtbox(195, 185, 0, 0)
      )),
      isOneShot = true
    )


    crouchKickSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_kick.png", 375, 200),
      3,
      6,
      actionsOnFrames = Some(HashMap(2->SpriteActions.ATTACK_BOTTOM)),
    )

    crouchHitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_hit.png", 176, 215),
      3,
      6
    )

    blockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/block.png", 219, 380),
      3,
      3,
      isOneShot = true
    )
    crouchBlockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_Block.png", 192, 250),
      2,
      1,
      isOneShot = true
    )

    dashSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/dash.png", 691, 386),
      1,
      7,
      Some(HashMap(0->SpriteActions.ATTACK_TOP)),
      hurtboxesOnFrames = Some(HashMap(
        0 -> Hurtbox(0, 0, 0, 0),
      )),
    )

    spritesLoaded = true
  }
  def getDashSpritesheet: SpriteConfig = dashSpritesheet

  override def getFlippableSpirtesheets: ArrayBuffer[SpriteConfig] = {
    val parentFlippables: ArrayBuffer[SpriteConfig] = super.getFlippableSpirtesheets

    parentFlippables.addOne(getDashSpritesheet)

    parentFlippables
  }
}
