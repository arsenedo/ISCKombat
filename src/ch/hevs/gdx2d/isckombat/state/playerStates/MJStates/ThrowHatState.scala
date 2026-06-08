package ch.hevs.gdx2d.isckombat.state.playerStates.MJStates

import ch.hevs.gdx2d.isckombat.entity.{Hat, Player}
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.MichaelJacksonSpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.{IdleState, PlayerState}
import com.badlogic.gdx.math.Vector2

class ThrowHatState extends PlayerState {
  private var hat: Hat = _

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(MichaelJacksonSpritesLoader.getHatThrowSpritesheet)

    owner.tryPlaySoundOnCurrentFrame()
  }

  override def update(owner: Player): Unit = {
    if (owner.getCurrentFrame == MichaelJacksonSpritesLoader.getHatThrowSpritesheet.nFrames) {
      val flipAdjustedPos: Vector2 = owner.getFlipAdjustedPosition
      val hatPosition: Vector2 = new Vector2(flipAdjustedPos.x, flipAdjustedPos.y + owner.getCurrentSpriteFrame.getRegionHeight.toFloat * 0.5f)
      hat = new Hat(owner.id, hatPosition, if (owner.getCurrentSpriteFrame.isFlipX) -1 else 1)

      EntityRegister.addEntity(hat)
      owner.updateState(new IdleState)
    }
  }

  override def exit(owner: Player): Unit = {}

  override def checkActiveCombo(player: Player): Unit = {}
}
