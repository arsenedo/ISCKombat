package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.MichaelJacksonSpritesLoader
import ch.hevs.gdx2d.isckombat.state.playerStates.MJStates.ThrowHatState
import ch.hevs.gdx2d.isckombat.state.playerStates.PlayerState
import com.badlogic.gdx.math.Vector2

import scala.collection.immutable.HashMap

class MichaelJackson(id: Int, position: Vector2) extends Player(id, position) {
  override def getSpritesLoader: SpritesLoader = {
    MichaelJacksonSpritesLoader
  }

  override def getCombinations: HashMap[String, Array[InputAction]] = {
    HashMap(
      "Smooth Criminal" -> Array(InputActions.CROUCH, InputActions.MOVE_LEFT, InputActions.PUNCH)
    )
  }

  override def getCombinationStates: HashMap[String, PlayerState] = {
    HashMap(
      "Smooth Criminal" -> new ThrowHatState
    )
  }
}
