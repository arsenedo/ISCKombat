package ch.hevs.gdx2d.isckombat.state

import ch.hevs.gdx2d.isckombat.character

class HitState(damage: Int) extends State {

  override def enter(c: character.Character): Unit = {
    c.updateSpritesheet(c.getSpritesLoader.getHitSpritesheet)

    c.applyDamage(damage)
  }

  override def update(c: character.Character): Unit = {
    if (c.getCurrentFrame == c.getCurrentSpriteConfig.nFrames) {
      c.updateState(new IdleState)
    }
  }

  override def exit(c: character.Character): Unit = {}

  override def handleKeyDown(keycode: Int, c: character.Character): Unit = {}

  override def handleKeyUp(keycode: Int, c: character.Character): Unit = {}
}
