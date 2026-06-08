package ch.hevs.gdx2d.isckombat.registers

import ch.hevs.gdx2d.isckombat.entity.{Entity, Player}

import scala.collection.mutable

object EntityRegister {
  var availableId = 0
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

  def findEnemy(player: Player): Option[Entity] = {
    entities.find(entity => entity.isInstanceOf[Player] && entity != player)
  }
}
