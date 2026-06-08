package ch.hevs.gdx2d.isckombat.utils

object GameMath {
  def clamp(value: Int, min: Int, max: Int): Int = {
    Math.min(max, Math.max(value, min))
  }
}
