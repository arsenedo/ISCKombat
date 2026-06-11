package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.JohnnySpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.JohnnyStates.DashState
import ch.hevs.gdx2d.isckombat.state.playerStates.PlayerState
import com.badlogic.gdx.math.Vector2

import scala.collection.immutable.HashMap

class Johnny(id: Int, position: Vector2) extends Player(id, position) {
  override def getSpritesLoader: SpritesLoader = {
    JohnnySpritesLoader
  }
  override def getCombinations: HashMap[String, Array[InputAction]] = {
    HashMap(
      "Dash" -> Array(InputActions.MOVE_LEFT, InputActions.MOVE_LEFT, InputActions.PUNCH)
    )
  }

  override def getCombinationStates: HashMap[String, PlayerState] = {
    HashMap(
      "Dash" -> new DashState
    )
  }
}
