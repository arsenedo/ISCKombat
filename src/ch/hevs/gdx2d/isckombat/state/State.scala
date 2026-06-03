package ch.hevs.gdx2d.isckombat.state

trait State[T] {
  def enter(owner: T): Unit
  def update(owner: T): Unit
  def exit(owner: T): Unit
}
