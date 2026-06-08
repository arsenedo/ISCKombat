package ch.hevs.gdx2d.isckombat.collision

import ch.hevs.gdx2d.isckombat.entity
import ch.hevs.gdx2d.isckombat.entity.{Hitbox, Player}
import ch.hevs.gdx2d.isckombat.registers.EntityRegister
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

object CollisionHandler {
  def detectCollisions(players: Array[entity.Entity]): mutable.HashMap[Hitbox, entity.Entity] = {
    val activeHitboxes = getHitboxes.filter(hitbox => hitbox.isActive)

    val detectedCollisions: mutable.HashMap[Hitbox, entity.Entity] = mutable.HashMap.empty
    for (hitbox <- activeHitboxes) {
      for (player <- players) {
        if ((player.id != hitbox.ownerId) && hitbox.isCollidingWith(player.position, player.getCurrentSpriteConfig.getHurtboxOnFrame(player.getCurrentFrame))) {
          detectedCollisions.addOne((hitbox, player))
        }
      }
    }

    detectedCollisions
  }

  // TODO CREATE A TRAIT FOR COLLIDABLES
  private def getHitboxes: mutable.Set[Hitbox] = {
    EntityRegister.entities.filter(entity => entity.getHitboxAtCurrentFrame.isDefined).map(entity => entity.getHitboxAtCurrentFrame.get)
  }
}
