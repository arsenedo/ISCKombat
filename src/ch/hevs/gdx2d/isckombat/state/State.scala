package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.character

trait State {
  def enter(c: character.Character): Unit
  def update(c: character.Character): Unit
  def exit(c: character.Character): Unit
  def handleKeyDown(keycode: Int, c: character.Character): Unit
  def handleKeyUp(keycode: Int, c: character.Character): Unit
}
