package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.HatSpritesLoader
import com.badlogic.gdx.math.Vector2

class Hat(id: Int, position: Vector2, val direction: Int) extends Entity(id, position) {
  private val translation: Vector2 = new Vector2(25 * direction, 0)

  updateSpritesheet(HatSpritesLoader.getHatSpritesheet)

  override def update(): Unit = {
    position.add(translation)
  }

  override def getSpritesLoader: SpritesLoader = HatSpritesLoader

  override def getFlipAdjustedPosition: Vector2 = {
    position
  }
}
