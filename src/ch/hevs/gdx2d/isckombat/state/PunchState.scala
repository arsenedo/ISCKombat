package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.character
import ch.hevs.gdx2d.isckombat.sprites.{SpriteActions, SpriteConfig}

import scala.collection.immutable.HashMap

class PunchState extends State {
  override def enter(c: character.Character): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getPunchSpritesheet)
  }

  override def update(c: character.Character): Unit = {
    val currentFrame: Int = c.getCurrentFrame
    val currentSprite: SpriteConfig = c.getCurrentSpriteConfig
    val actionsOnFramesOption: Option[HashMap[Int, SpriteActions.Action]] = currentSprite.actionsOnFrames

    if (actionsOnFramesOption.isDefined) {
      val frameActionOption: Option[SpriteActions.Action] = actionsOnFramesOption.get.get(currentFrame)

      if (frameActionOption.isDefined) {
        c.activateHitbox(frameActionOption.get)
      } else {
        c.deactivateHitbox()
      }
    }

    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new IdleState)
    }
  }

  override def exit(c: character.Character): Unit = {
    c.deactivateHitbox()
  }

  override def handleKeyDown(keycode: Int, c: character.Character): Unit = {

  }

  override def handleKeyUp(keycode: Int, c: character.Character): Unit = {

  }
}
