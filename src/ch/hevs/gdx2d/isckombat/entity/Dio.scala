package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.DioSpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.DioStates.StandAttackState
import ch.hevs.gdx2d.isckombat.state.playerStates.PlayerState
import com.badlogic.gdx.math.Vector2

import scala.collection.immutable.HashMap

class Dio(id: Int, position: Vector2) extends Player(id, position) {

  override def getSpritesLoader: SpritesLoader = {
    DioSpritesLoader
  }

  override def getCombinations: HashMap[String, Array[InputAction]] = {
    HashMap(
      "Muda Muda Muda Muda" -> Array(InputActions.CROUCH, InputActions.CROUCH, InputActions.PUNCH)
    )
  }

  override def getCombinationStates: HashMap[String, PlayerState] = {
    HashMap(
      "Muda Muda Muda Muda" -> new StandAttackState
    )
  }
}
