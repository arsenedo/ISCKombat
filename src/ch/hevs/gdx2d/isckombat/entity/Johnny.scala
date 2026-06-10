package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.JohnnySpritesLoader
import com.badlogic.gdx.math.Vector2

class Johnny(id: Int, position: Vector2) extends Player(id, position) {
  override def getSpritesLoader: SpritesLoader = {
    JohnnySpritesLoader
  }
}
