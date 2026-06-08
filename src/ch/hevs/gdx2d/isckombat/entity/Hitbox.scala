package ch.hevs.gdx2d.isckombat.entity

import com.badlogic.gdx.math.Vector2

case class Hitbox(private var offset: Vector2, width: Int, height: Int, ownerId: Int, getEntityPosition: () => Vector2) {
  var isActive: Boolean = true

  def isCollidingWith(withPosition: Vector2, hurtbox: Hurtbox): Boolean = {
    val hurtboxGlobalX = withPosition.x + hurtbox.offsetX
    val hurtboxGlobalY = withPosition.y + hurtbox.offsetY

    val position = getGlobalPosition
    (
      ((position.x + width > hurtboxGlobalX) && (position.x < hurtboxGlobalX + hurtbox.width ))
      && ((position.y + height > hurtboxGlobalY) && (position.y < hurtboxGlobalY + hurtbox.height))
    )
  }

  def getGlobalPosition: Vector2 = {
    val entityPosition: Vector2 = getEntityPosition()

    new Vector2(
      entityPosition.x + offset.x,
      entityPosition.y + offset.y
    )
  }
}
