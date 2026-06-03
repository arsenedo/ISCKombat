package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.{Entity, Player}
import ch.hevs.gdx2d.isckombat.sprites.SpriteConfig
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class JumpState(direction: Int = 0) extends PlayerState {
  val GRAVITY: Float = 2.5f;

  var velocity: Vector2 = new Vector2(10 * direction, 0f)

  override def enter(owner: Player): Unit = {
    if (direction == 0) {
      owner.updateSpritesheet(owner.getSpritesLoader.getJumpSpritesheet)
    } else {
      val directionalJumpSpritesheet: SpriteConfig = owner.getSpritesLoader.getDirectionalJumpSpritesheet
      owner.updateSpritesheet(directionalJumpSpritesheet)
      owner.getSpritesLoader.setSpritesheetFlipState(directionalJumpSpritesheet.spritesheet, direction < 0)
    }

    owner.tryPlaySoundOnCurrentFrame()

    velocity.y = 50f
  }

  override def update(owner: Player): Unit = {
    owner.position.add(velocity)

    velocity.y -= GRAVITY

    if (owner.position.y <= 100) {
      owner.position.y = 100
      owner.updateState(new IdleState)
    }
  }

  override def exit(owner: Player): Unit = {

  }

  override def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val lastFrame: Int = player.getCurrentSpriteConfig.nFrames - 1
    if (player.getCurrentFrame < lastFrame) {
      super.drawSprite(g, player)
    } else {
      val flipAdjustedPos = player.getFlipAdjustedPosition
      g.draw(player.getCurrentSpriteConfig.spritesheet.sprites(0)(lastFrame), flipAdjustedPos.x, flipAdjustedPos.y)
    }
  }

  override def receiveDamage(player: Player, damage: Int): Unit = {
    player.updateState(new AerialHitState(if (player.getCurrentSpriteFrame.isFlipX) 1 else -1, damage))
  }
}
