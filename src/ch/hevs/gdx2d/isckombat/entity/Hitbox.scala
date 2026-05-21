package ch.hevs.gdx2d.isckombat.entity

import com.badlogic.gdx.math.Vector2

case class Hitbox(var position: Vector2, width: Int, height: Int, ownerId: Int) {
  var isActive: Boolean = true

  def isCollidingWith(withPosition: Vector2, withWidth: Int, withHeight: Int): Boolean = {
    (
      ((position.x + width > withPosition.x) && (position.x < withPosition.x + withWidth))
      && ((position.y + height > withPosition.y) && (position.y < withPosition.y + withHeight))
    )
  }
}
