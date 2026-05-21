package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.entity
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2

class WalkState(private var executorKeycode: Int) extends State {
  private var direction = if (executorKeycode == Input.Keys.D || executorKeycode == Input.Keys.RIGHT) 1 else -1
  private val translationVector = new Vector2(5, 0)

  override def enter(c: entity.Entity): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getWalkSpritesheet)
  }

  override def update(c: entity.Entity): Unit = {
    c.position.add(translationVector.x * direction, translationVector.y)
  }

  override def exit(c: entity.Entity): Unit = {

  }

  override def handleKeyDown(keycode: Int, c: entity.Entity): Unit = {
    if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
      direction = -1
      executorKeycode = keycode
    } else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
      direction = 1
      executorKeycode = keycode
    }

    if (keycode == Input.Keys.U || keycode == Input.Keys.NUMPAD_4) {
      c.updateState(new PunchState)
    }
  }

  override def handleKeyUp(keycode: Int, c: entity.Entity): Unit = {
    if (keycode == executorKeycode) {
      c.updateState(new IdleState)
    }
  }
}
