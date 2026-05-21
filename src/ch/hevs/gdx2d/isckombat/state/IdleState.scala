package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity
import com.badlogic.gdx.Input

class IdleState() extends State {

  override def enter(c: entity.Entity): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getIdleSpritesheet)
  }

  override def update(c: entity.Entity): Unit = {

  }

  override def exit(c: entity.Entity): Unit = {

  }

  override def handleKeyDown(keycode: Int, c: entity.Entity): Unit = {
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

  override def handleKeyUp(keycode: Int, c: entity.Entity): Unit = {
  }
}
