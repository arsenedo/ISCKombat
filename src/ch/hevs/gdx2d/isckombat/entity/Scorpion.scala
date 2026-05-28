package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.Controllable
import ch.hevs.gdx2d.isckombat.sprites.{ScorpionSpritesLoader, SpritesLoader}
import com.badlogic.gdx.math.Vector2

class Scorpion(id: Int, position: Vector2) extends Player(id, position) {
  override def getSpritesLoader: SpritesLoader = {
    ScorpionSpritesLoader
  }
}
