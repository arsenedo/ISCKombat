package ch.hevs.gdx2d.isckombat.utils

object GameMath {
  def clamp(value: Int, min: Int, max: Int): Int = {
    Math.min(max, Math.max(value, min))
  }

  def nanoToMs(nanoTime: Long): Double = {
    nanoTime / math.pow(10, 6)
  }

  def nanoToS(nanoTime: Long): Double = {
    nanoTime / math.pow(10, 9)
  }
}
