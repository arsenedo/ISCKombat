package ch.hevs.gdx2d.isckombat.character

import ch.hevs.gdx2d.isckombat.sprites.{MichaelJacksonSpritesLoader, SpritesLoader}
import com.badlogic.gdx.math.Vector2

class MichaelJackson(position: Vector2) extends Character(position) {
  override def getSpritesLoader: SpritesLoader = {
    MichaelJacksonSpritesLoader
  }
}
