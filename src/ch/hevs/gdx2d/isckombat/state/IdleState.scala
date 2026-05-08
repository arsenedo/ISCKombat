package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.character
import com.badlogic.gdx.Input

class IdleState() extends State {

  override def enter(c: character.Character): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getIdleSpritesheet)
  }

  override def update(c: character.Character): Unit = {

  }

  override def exit(c: character.Character): Unit = {

  }

  override def handleKeyDown(keycode: Int, c: character.Character): Unit = {
    if (keycode == Input.Keys.D || keycode == Input.Keys.A) {
      c.updateState(new WalkState(keycode))
    }

    if (keycode == Input.Keys.U) {
      c.updateState(new PunchState)
    }
  }

  override def handleKeyUp(keycode: Int, c: character.Character): Unit = {
  }
}
