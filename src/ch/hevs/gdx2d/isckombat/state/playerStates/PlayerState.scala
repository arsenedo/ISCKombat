package ch.hevs.gdx2d.isckombat.state.playerStates

import ch.hevs.gdx2d.isckombat.entity.Player
import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction
import ch.hevs.gdx2d.isckombat.entity.inputs.InputCommand
import ch.hevs.gdx2d.isckombat.state.State
import ch.hevs.gdx2d.isckombat.state.playerStates.ScorpionStates.ThrowSpearState
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.immutable.HashMap

trait PlayerState extends State[Player] {
  def drawSprite(g: GdxGraphics, player: Player): Unit = {
    val flipAdjustedPos = player.getFlipAdjustedPosition
    g.draw(player.getCurrentSpriteFrame, flipAdjustedPos.x, flipAdjustedPos.y)
  }

  def receiveDamage(player: Player, damage: Int): Unit = {
    player.updateState(new HitState(damage))
  }

  def checkActiveCombo(player: Player): Unit = {
    val inputHistory: Array[InputCommand] = player.inputsHandler.getHistory.toArray
    val historyLength: Int = inputHistory.length

    val combos: HashMap[String, Array[InputAction]] = player.getCombinations

    combos.foreachEntry((name, combination) => {
      val latestInputs: Array[InputCommand] = inputHistory.slice(historyLength - combination.length, historyLength)
      if (
        (latestInputs.map((command) => command.action).sameElements(combination))
          && (!latestInputs(latestInputs.length - 1).hasActivatedACombo)) {
        if (areInputsComboActive(latestInputs)) {
          latestInputs(latestInputs.length - 1).hasActivatedACombo = true
          player.updateState(player.getCombinationStates(name))
        }
      }

    })
  }

  private def areInputsComboActive(inputs: Array[InputCommand]): Boolean = {
    for (i <- 1 until inputs.length) {
      val previousCommand = inputs(i - 1)
      val currentCommand = inputs(i)
      if (
        (!previousCommand.compare(currentCommand.getCreatedAt))
          || ((i == inputs.length - 1) && (!currentCommand.isActive))
      ) {
        return false
      }
    }

    true
  }
}
