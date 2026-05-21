package ch.hevs.gdx2d.isckombat.registers

import ch.hevs.gdx2d.isckombat.entity.Entity

import scala.collection.mutable

object EntityRegister {
  val entities: mutable.Set[Entity] = mutable.Set.empty

  def addEntity(entity: Entity): Boolean = {
    entities.add(entity)
  }

  def removeEntity(entity: Entity): Boolean = {
    entities.remove(entity)
  }

  def findById(id: Int): Option[Entity] = {
    entities.find(entity => entity.id == id)
  }
}
