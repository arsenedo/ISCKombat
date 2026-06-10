package ch.hevs.gdx2d.isckombat.state.playerStates.DioStates

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.isckombat.entity.{Player, TheWorld}
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.{DioSpritesLoader, TheWorldSpritesLoader}
import ch.hevs.gdx2d.isckombat.state.playerStates.{IdleState, PlayerState}
import com.badlogic.gdx.math.Vector2

class StandAttackState extends PlayerState {
  private val standTravelDistance: Float = 250
  private val standTravelVelocity: Float = 20
  private var theWorld: TheWorld = _
  private val allowedOffset: Float = 20

  private var direction: Float = 0
  private var targetPosition: Float = 0

  private var theWorldIsAttacking: Boolean = false

  private val theWorldSample = new SoundSample("data/sounds/characters/dio/the world.wav")
  private val mudamudaSample = new SoundSample("data/sounds/characters/dio/muda muda muda muda.wav")

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(DioSpritesLoader.getStanceSpritesheet)

    direction = if (owner.getCurrentSpriteFrame.isFlipX) -1 else 1

    theWorld = new TheWorld(owner.id, owner.getFlipAdjustedPosition, direction)

    targetPosition = owner.getFlipAdjustedPosition.x + direction * standTravelDistance - (if (direction == -1) owner.getCurrentSpriteFrame.getRegionWidth else 0)

    EntityRegister.addEntity(theWorld)

    theWorldSample.play()
  }

  override def update(owner: Player): Unit = {
    if (
      (theWorld.position.x >  targetPosition - allowedOffset)
      && (theWorld.position.x < targetPosition + theWorld.getCurrentSpriteFrame.getRegionWidth + allowedOffset)
      && (!theWorldIsAttacking) // Enter attacking state
    ) {
      theWorld.updateSpritesheet(TheWorldSpritesLoader.getAttackSpritesheet)
      theWorldIsAttacking = true
      mudamudaSample.play()
    } else if (!theWorldIsAttacking) {
      theWorld.position.add(new Vector2(standTravelVelocity * direction, 0))
    }

    if (theWorldIsAttacking && theWorld.getCurrentFrame >= theWorld.getCurrentSpriteConfig.nFrames - 1) {
      owner.updateState(new IdleState)
    }
  }

  override def exit(owner: Player): Unit = {
    theWorld.onDelete()

    theWorldSample.dispose()
    mudamudaSample.dispose()
  }

  override def checkActiveCombo(player: Player): Unit = {}
}
