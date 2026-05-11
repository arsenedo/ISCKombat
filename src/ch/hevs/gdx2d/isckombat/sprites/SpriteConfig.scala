package ch.hevs.gdx2d.isckombat.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import SpriteActions.Action
import scala.collection.immutable

case class SpriteConfig(
  spritesheet: Spritesheet,
  nFrames: Int,
  ticksPerFrame: Int,
  actionsOnFrames: Option[immutable.HashMap[Int, Action]] = None
)
