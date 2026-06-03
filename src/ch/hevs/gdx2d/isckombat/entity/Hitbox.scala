package ch.hevs.gdx2d.isckombat.entity

import com.badlogic.gdx.math.Vector2

case class Hitbox(var position: Vector2, width: Int, height: Int, ownerId: Int) {
  var isActive: Boolean = true

  def isCollidingWith(withPosition: Vector2, hurtbox: Hurtbox): Boolean = {
    val hurtboxGlobalX = withPosition.x + hurtbox.offsetX
    val hurtboxGlobalY = withPosition.y + hurtbox.offsetY
    (
      ((position.x + width > hurtboxGlobalX) && (position.x < hurtboxGlobalX + hurtbox.width ))
      && ((position.y + height > hurtboxGlobalY) && (position.y < hurtboxGlobalY + hurtbox.height))
    )
  }
}
