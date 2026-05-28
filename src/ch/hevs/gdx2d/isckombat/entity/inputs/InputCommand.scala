package ch.hevs.gdx2d.isckombat.entity.inputs

import ch.hevs.gdx2d.isckombat.entity.inputs.InputActions.InputAction

case class InputCommand(action: InputAction, var isExecuted: Boolean)