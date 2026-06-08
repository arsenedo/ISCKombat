package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.{Controllable, InputActions}
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.ScorpionSpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.PlayerState
import ch.hevs.gdx2d.isckombat.state.playerStates.ScorpionStates.ThrowSpearState
import com.badlogic.gdx.math.Vector2

import scala.collection.immutable.HashMap

class Scorpion(id: Int, position: Vector2) extends Player(id, position) {
  override def getSpritesLoader: SpritesLoader = {
    ScorpionSpritesLoader
  }

  override def getCombinations: HashMap[String, Array[InputAction]] = {
    HashMap(
      "Get Over Here" -> Array(InputActions.MOVE_LEFT, InputActions.MOVE_LEFT, InputActions.PUNCH)
    )
  }

  override def getCombinationStates: HashMap[String, PlayerState] = {
    HashMap(
      "Get Over Here" -> new ThrowSpearState
    )
  }
}
