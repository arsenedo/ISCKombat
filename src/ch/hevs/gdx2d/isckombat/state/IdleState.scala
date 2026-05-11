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
    if (
      (keycode == Input.Keys.D || keycode == Input.Keys.A)
      || (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT)
    ) {
      c.updateState(new WalkState(keycode))
    }

    if (
      (keycode == Input.Keys.U)
      || (keycode == Input.Keys.NUMPAD_4)
    ) {
      c.updateState(new PunchState)
    }
  }

  override def handleKeyUp(keycode: Int, c: character.Character): Unit = {
  }
}
