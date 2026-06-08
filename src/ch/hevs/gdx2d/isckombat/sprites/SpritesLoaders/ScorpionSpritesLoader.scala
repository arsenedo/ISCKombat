package ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.isckombat.entity.Hurtbox
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig, SpritesLoader}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

object ScorpionSpritesLoader extends SpritesLoader {
  private var spritesLoaded = false

  private var spearThrowSpritesheet: SpriteConfig = _

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

    jumpSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/jump.png", 186, 275),
      1,
      1
    )

    directionalJump = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/directional_jump.png", 188, 200),
      7,
      5
    )

    fallingSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/knockout.png", 360, 370),
      5,
      6
    )

    victorySpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/victory.png", 187, 420),
      4,
      5
    )

    crouchSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch.png", 195, 330),
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
      new Spritesheet(s"$spritesheetsPath/crouch_kick.png", 340, 200),
      4,
      6,
      actionsOnFrames = Some(HashMap(3->SpriteActions.ATTACK_BOTTOM)),
    )

    crouchHitSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_hit.png", 231, 215),
      3,
      6
    )

    blockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/block.png", 198, 380),
      3,
      3,
      isOneShot = true
    )
    crouchBlockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_Block.png", 162, 250),
      1,
      1
    )

    spearThrowSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/throw_spear.png", 394, 375),
      6,
      7
    )

    SpearSpritesLoader.loadSpritesheets() // Load dependency spritesheets

    spritesLoaded = true
  }

  def getSpearThrowSpritesheet: SpriteConfig = spearThrowSpritesheet

  override def getFlippableSpirtesheets: ArrayBuffer[SpriteConfig] = {
    val parentFlippables: ArrayBuffer[SpriteConfig] = super.getFlippableSpirtesheets

    parentFlippables.addOne(getSpearThrowSpritesheet)

    parentFlippables
  }
}
