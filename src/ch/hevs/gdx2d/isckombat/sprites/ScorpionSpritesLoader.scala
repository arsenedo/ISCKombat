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
      2
    )

    crouchKickSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_kICK.png", 260, 270),
      4,
      6
    )

    blockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/block.png", 198, 380),
      3,
      3
    )
    crouchBlockSpritesheet = SpriteConfig(
      new Spritesheet(s"$spritesheetsPath/crouch_Block.png", 162, 250),
      1,
      1
    )

    spritesLoaded = true
  }
}
