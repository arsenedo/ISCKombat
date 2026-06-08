package ch.hevs.gdx2d.isckombat.state.playerStates.ScorpionStates

import ch.hevs.gdx2d.isckombat.entity.{Entity, Player, Spear}
import ch.hevs.gdx2d.isckombat.state.playerStates.{IdleState, PlayerState, FreezeState, UncontrolledWalkState}
import ch.hevs.gdx2d.lib.GdxGraphics
import ThrowSpearStages.ThrowSpearStage
import ch.hevs.gdx2d.components.audio.{MusicPlayer, SoundSample}
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.{ScorpionSpritesLoader, SpearSpritesLoader}
import ch.hevs.gdx2d.isckombat.utils.GameMath
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

private object ThrowSpearStages extends Enumeration {
  type ThrowSpearStage = Value

  val SPEAR_THROW, ENEMY_CATCH, ENEMY_PULL = Value
}

class ThrowSpearState extends PlayerState {
  private var currentStage: ThrowSpearStage = _
  private var spear: Spear = _
  private var target: Player = _
  private val getOverHereSound: MusicPlayer = new MusicPlayer("data/sounds/characters/scorpion/get_over_here.wav")

  override def enter(owner: Player): Unit = {
    owner.updateSpritesheet(ScorpionSpritesLoader.getSpearThrowSpritesheet)

    currentStage = ThrowSpearStages.SPEAR_THROW

    val flipAdjustedPos: Vector2 = owner.getFlipAdjustedPosition
    val direction = if (owner.getCurrentSpriteFrame.isFlipX) -1 else 1
    val spearWidth: Int = SpearSpritesLoader.getThrownSpearSpritesheet.spritesheet.sprites(0)(0).getRegionWidth
    val spearStartPosX = flipAdjustedPos.x + owner.getCurrentSpriteFrame.getRegionWidth / 2 - (if (direction == 1) spearWidth else 0)
    val spearPosition: Vector2 = new Vector2(spearStartPosX, flipAdjustedPos.y + owner.getCurrentSpriteFrame.getRegionHeight.toFloat * 0.7f)
    spear = new Spear(owner.id, spearPosition, direction)

    EntityRegister.addEntity(spear)

    target = EntityRegister.findEnemy(owner).get.asInstanceOf[Player]
  }

  override def update(owner: Player): Unit = {
    currentStage match {
      case ThrowSpearStages.SPEAR_THROW => {

        if (
          (spear.position.x >= owner.getFlipAdjustedPosition.x + owner.getCurrentSpriteFrame.getRegionWidth)
          || (spear.position.x + spear.getCurrentSpriteFrame.getRegionWidth <= owner.getFlipAdjustedPosition.x)
        ) {
          // Spear flew too far, release it from the hand
          owner.updateState(new IdleState)
          return
        }

        if (spear.getHitboxAtCurrentFrame.get.isCollidingWith(target.position, target.getHurtboxAtCurrentFrame)) {
          currentStage = ThrowSpearStages.ENEMY_CATCH
          target.updateState(new FreezeState)
          spear.translation = new Vector2(0, 0)
          spear.updateSpritesheet(SpearSpritesLoader.getCaughtSpearSpritesheet)
          spear.position.add(new Vector2(0, 25))
          getOverHereSound.play()
          return
        }
      }
      case ThrowSpearStages.ENEMY_CATCH => {
        if (owner.currentFrame >= 3 && !getOverHereSound.isPlaying) {
          currentStage = ThrowSpearStages.ENEMY_PULL
        }
      }
      case ThrowSpearStages.ENEMY_PULL => {
        target.updateState(new UncontrolledWalkState(owner.getFlipAdjustedPosition.x + owner.getCurrentSpriteFrame.getRegionWidth / 2, 150f, 3f))
        if (owner.currentFrame >= owner.getCurrentSpriteConfig.nFrames) {
          spear.onDelete()
          owner.updateState(new IdleState)
        }
      }
    }
  }

  override def exit(owner: Player): Unit = {}

  override def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val flipAdjustedPos = player.getFlipAdjustedPosition
    val currentFrame = player.currentFrame

    var min = 0
    var max = 0
    currentStage match {
      case ThrowSpearStages.SPEAR_THROW => {
        min = 0
        max = 1
      }
      case ThrowSpearStages.ENEMY_CATCH => {
        min = 2
        max = 3
      }

      case ThrowSpearStages.ENEMY_PULL => {
        min = 4
        max = 5
      }
    }

    val clampedFrame: Int = GameMath.clamp(currentFrame, min ,max)
    player.currentFrame = clampedFrame
    super.drawSprite(g, player)
  }

  override def checkActiveCombo(player: Player): Unit = {}
}
