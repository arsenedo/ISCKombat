package ch.hevs.gdx2d.isckombat.entity

import ch.hevs.gdx2d.isckombat.sprites.SpritesLoader
import ch.hevs.gdx2d.isckombat.sprites.SpritesLoaders.TheWorldSpritesLoader
import com.badlogic.gdx.math.Vector2

class TheWorld(id: Int, position: Vector2, val direction: Float) extends Entity(id, position) {
  updateSpritesheet(TheWorldSpritesLoader.getFlySpritesheet)

  flipSprites()
  override def getSpritesLoader: SpritesLoader = TheWorldSpritesLoader

  override def getFlipAdjustedPosition: Vector2 = {
    if (!getCurrentSpriteFrame.isFlipX) {
      return new Vector2(position.x, position.y)
    }

    val flyFrameWidth: Int = TheWorldSpritesLoader.getFlySpritesheet.spritesheet.sprites(0)(0).getRegionWidth
    val currentSpriteFrameWidth: Int = getCurrentSpriteFrame.getRegionWidth

    val dx = currentSpriteFrameWidth - flyFrameWidth

    new Vector2(position.x - dx, position.y)
  }

  def flipSprites(): Unit = {
    getSpritesLoader.setAllSpritesFlipState(direction < 0)
  }
}
