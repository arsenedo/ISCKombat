package ch.hevs.gdx2d.isckombat.interface

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.components.bitmaps.BitmapImage

case class Stage(name: String) {
  val stagesDirectoryPath = s"data/stages/$name"

  val background: BitmapImage = new BitmapImage(s"$stagesDirectoryPath/background.png")
  val music: MusicPlayer = new MusicPlayer(s"$stagesDirectoryPath/music.mp3")
}
