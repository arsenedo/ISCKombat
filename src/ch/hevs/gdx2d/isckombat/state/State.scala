package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity

trait State {
  def enter(c: entity.Entity): Unit
  def update(c: entity.Entity): Unit
  def exit(c: entity.Entity): Unit
  def handleKeyDown(keycode: Int, c: entity.Entity): Unit
  def handleKeyUp(keycode: Int, c: entity.Entity): Unit
}
