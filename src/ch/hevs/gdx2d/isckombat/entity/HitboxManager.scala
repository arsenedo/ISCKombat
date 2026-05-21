package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.SpriteConfig
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ch.hevs.gdx2d.isckombat.sprites.SpriteActions
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

class HitboxManager(entityId: Int, getEntityPosition: () => Vector2) {
  private val framesToHitboxes: mutable.HashMap[Int, Hitbox] = mutable.HashMap.empty

  def updateHitboxesMap(spriteConfig: SpriteConfig) = {
    val sprite: TextureRegion = spriteConfig.spritesheet.sprites(0)(0)
    val spriteWidth: Int = sprite.getRegionWidth
    val spriteHeight: Int = sprite.getRegionHeight

    if (spriteConfig.actionsOnFrames.isDefined) {
      spriteConfig.actionsOnFrames.get.foreachEntry((frame, action) => {
        framesToHitboxes.addOne((frame, createHitbox(action, spriteWidth, spriteHeight)))
      })
    }
  }

  def clearHitboxesMap(): Unit = {
    framesToHitboxes.clear()
  }

  def getHitboxAtFrame(frame: Int): Option[Hitbox] = {
    framesToHitboxes.get(frame)
  }

  private def createHitbox(action: SpriteActions.Action, spriteWidth: Int, spriteHeight: Int): Hitbox = {
    val hitboxOffset: Vector2 = {
      action match {
        case SpriteActions.ATTACK_TOP => new Vector2(0, spriteHeight.toFloat * (2f/3f))
        case SpriteActions.ATTACK_MID => new Vector2(0, spriteHeight.toFloat * (1f/3f))
        case SpriteActions.ATTACK_BOTTOM => new Vector2(0, 0)
        case _ => new Vector2(0, 0)
      }
    }

    val entityPosition: Vector2 = getEntityPosition()
    val position = new Vector2(entityPosition.x + hitboxOffset.x, entityPosition.y + hitboxOffset.y)

    Hitbox(position, spriteWidth, (spriteHeight.toFloat * 1f/3f).toInt, entityId)
  }
}
