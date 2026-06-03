package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.config.Constants
import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class AerialHitState(direction: Int, damage: Int) extends PlayerState {
  var velocity: Vector2 = new Vector2(15 * direction, 0f)

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(owner.getSpritesLoader.getFallingSpritesheet)

    owner.applyDamage(damage)

    velocity.y = 25
  }

  override def update(owner: Player): Unit = {
    if (owner.position.y > Constants.GROUND_Y_LEVEL) {
      owner.position.add(velocity)

      velocity.y -= Constants.GRAVITY
    } else {
      owner.position.y = Constants.GROUND_Y_LEVEL

      owner.updateState(new LayState(Constants.FPS * 1))
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
