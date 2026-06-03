package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import SpriteActions.Action
import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.isckombat.entity.Hurtbox

import scala.collection.immutable

case class SpriteConfig(
  spritesheet: Spritesheet,
  nFrames: Int,
  ticksPerFrame: Int,
  actionsOnFrames: Option[immutable.HashMap[Int, Action]] = None,
  hurtboxesOnFrames: Option[immutable.HashMap[Int, Hurtbox]] = None,
  soundOnFrames: Option[immutable.HashMap[Int, SoundSample]] = None,
  isOneShot: Boolean = false
) {
  def getHurtboxOnFrame(frame: Int): Hurtbox = {
    val moduloFrame = frame % nFrames
    if (hurtboxesOnFrames.isEmpty || !hurtboxesOnFrames.get.contains(moduloFrame)) {
      val currentFrame = spritesheet.sprites(0)(moduloFrame)

      return Hurtbox(currentFrame.getRegionWidth, currentFrame.getRegionHeight, 0, 0)
    }

    hurtboxesOnFrames.get(moduloFrame)
  }
}
