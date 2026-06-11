package ch.hevs.gdx2d.isckombat.state.playerStates.JohnnyStates

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.JohnnySpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.{AerialHitState, IdleState, KnockoutState, PlayerState}
import com.badlogic.gdx.math.Vector2

class DashState extends PlayerState{
  private val offset_X = 500f
  private var direction : Float = 0
  private var movement : Vector2 = new Vector2(offset_X * direction, 0)
  private var target : Player = _
  private val dashSample = new SoundSample("data/sounds/characters/johnny/shadowKick.wav")

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(JohnnySpritesLoader.getDashSpritesheet)
    target = EntityRegister.findEnemy(owner).get.asInstanceOf[Player]
    dashSample.play()
  }
  def update(owner: Player): Unit = {
    direction = if (owner.getCurrentSpriteFrame.isFlipX) -1 else 1
    movement = new Vector2(offset_X * direction, 0)

    if (owner.getCurrentFrame == owner.getCurrentSpriteConfig.nFrames) {
      if (owner.getHitboxAtCurrentFrame.get.isCollidingWith(target.position, target.getHurtboxAtCurrentFrame)) {
        target.position.add(new Vector2(0f, 100f))
        target.updateState(new AerialHitState(direction.toInt, 50))
      }
      owner.updateState(new IdleState)
      owner.position.add(movement)
      return
    }
  }
  def exit(owner: Player): Unit = {}
}
