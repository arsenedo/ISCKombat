package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.SpearSpritesLoader
import com.badlogic.gdx.math.Vector2

class Spear(id: Int, position: Vector2, val direction: Int) extends Entity(id, position) {
  var translation: Vector2 = new Vector2(30f * direction, 0)

  updateSpritesheet(SpearSpritesLoader.getThrownSpearSpritesheet)

  flipSprites()
  override def update(): Unit = {
    position.add(translation)
  }

  def flipSprites(): Unit = {
    getSpritesLoader.setAllSpritesFlipState(direction < 0)
  }

  override def getFlipAdjustedPosition: Vector2 = {
    position

  }

  override def getSpritesLoader: SpritesLoader = SpearSpritesLoader
}
