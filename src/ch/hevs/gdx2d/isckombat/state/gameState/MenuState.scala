package ch.hevs.gdx2d.isckombat.state.gameState

import ch.hevs.gdx2d.components.audio.{MusicPlayer, SoundSample}
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.isckombat.Game
import ch.hevs.gdx2d.isckombat.entity.{Player, Scorpion}
import ch.hevs.gdx2d.isckombat.interface.CharacterSelector
import ch.hevs.gdx2d.isckombat.utils.GameMath
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.{Controller, PovDirection}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

private case class PlayerSelection(id: Int, cell: Array[Int]) {
  var character: Option[Player] = None
}

class MenuState extends GameState {
  private val player1Selection: PlayerSelection = PlayerSelection(0, Array(0, 0))
  private val player2Selection: PlayerSelection = PlayerSelection(1, Array(1, 0))

  private var characterGrid: Array[Array[CharacterSelector]] = _

  private var menuBackground: BitmapImage = _

  private var backgroundMusic : MusicPlayer = _

  private var bothPlayersSelectedTimestamp: Double = 0

  val GRID_GAP = 10
  val CELL_WIDTH = 400
  val CELL_HEIGHT = 500
  var GRID_START_X = 0
  var GRID_START_Y = 0

  var CHARACTER_DISPLAY_X_OFFSET = 100
  var CHARACTER_DISPLAY_Y_OFFSET = 250

  var START_MATCH_TIMER_S = 5

  override def enter(owner: Game): Unit = {
    menuBackground = new BitmapImage("data/images/menu_background.png")

    backgroundMusic = new MusicPlayer("data/sounds/misc/theme.mp3")

    characterGrid = Array(
      Array(
        CharacterSelector("Scorpion", new BitmapImage("data/images/icons/scorpion.png"), new SoundSample("data/sounds/characters/scorpion/your_soul_is_weak.wav")),
        CharacterSelector("Dio", new BitmapImage("data/images/icons/dio.png"), new SoundSample("data/sounds/characters/dio/laugh.wav"))
      ),
      Array(
        CharacterSelector("Michael Jackson", new BitmapImage("data/images/icons/mj.png"), new SoundSample("data/sounds/characters/mj/hee-hee.wav")),
        CharacterSelector("Johnny", new BitmapImage("data/images/icons/johnny.png"), new SoundSample("data/sounds/characters/johnny/oh_yeah.wav"))
      )
    )

    GRID_START_X = owner.getWindowWidth / 2 - GRID_GAP / 2 - CELL_WIDTH / 2
    GRID_START_Y = owner.getWindowHeight / 2 + CELL_HEIGHT / 2

    backgroundMusic.play()
  }

  override def update(owner: Game): Unit = {
    if (player1Selection.character.isDefined) {
      player1Selection.character.get.update(new Vector2(owner.getWindowWidth/2, 0))
    }

    if (player2Selection.character.isDefined) {
      player2Selection.character.get.update(new Vector2(owner.getWindowWidth/2, 0))
    }

    if (player1Selection.character.isDefined && player2Selection.character.isDefined) {
      if (bothPlayersSelectedTimestamp == 0) {
        bothPlayersSelectedTimestamp = GameMath.nanoToS(System.nanoTime())
      }

      if (bothPlayersSelectedTimestamp + START_MATCH_TIMER_S <= GameMath.nanoToS(System.nanoTime())) {
        println("Start match!")
        owner.updateGameState(new MatchState(player1Selection.character.get, player2Selection.character.get))
      }
    }
  }

  override def exit(owner: Game): Unit = {
    backgroundMusic.stop()
    backgroundMusic.dispose()
  }

  override def onGraphicsRender(g: GdxGraphics, game: Game): Unit = {
    g.drawPicture(game.getWindowWidth / 2 + 140, game.getWindowHeight / 2, menuBackground)

    drawCharacterIcons(g)

    drawPlayerSelection(g, player1Selection.cell, Color.BLUE)
    drawPlayerSelection(g, player2Selection.cell, Color.GREEN)

    if (player1Selection.character.isDefined) {
      player1Selection.character.get.drawSprite(g)
    }

    if (player2Selection.character.isDefined) {
      player2Selection.character.get.drawSprite(g)
    }
  }

  override def onHandleKeyDown(key: Int): Unit = {
    handlePlayersGridNavigation(key)

    handlePlayersCharacterSelection(key)
  }

  override def onHandleKeyUp(key: Int): Unit = {}

  private def drawCharacterIcons(g: GdxGraphics): Unit = {
    for ((row, i) <- characterGrid.zipWithIndex) {
      for ((characterSelector, j) <- row.zipWithIndex) {
        g.drawPicture(
          GRID_START_X + (CELL_WIDTH + GRID_GAP) * j,
          GRID_START_Y - (CELL_HEIGHT + GRID_GAP) * i,
          characterSelector.icon
        )
      }
    }
  }

  private def drawPlayerSelection(g: GdxGraphics, cell: Array[Int], color: Color): Unit = {
    g.setColor(color)
    g.setPenWidth(5)
    g.drawRectangle(
      GRID_START_X + (CELL_WIDTH + GRID_GAP) * cell(0),
      GRID_START_Y - (CELL_HEIGHT + GRID_GAP) * cell(1),
      CELL_WIDTH,
      CELL_HEIGHT,
      0
    )
  }

  private def handlePlayersGridNavigation(key: Int): Unit = {
    if (key == Input.Keys.W || key == Input.Keys.S) {
      val yDirection: Int = if (key == Input.Keys.W) -1 else 1
      player1Selection.cell(1) = Math.abs(player1Selection.cell(1) + yDirection) % 2
    }
    if (key == Input.Keys.D || key == Input.Keys.A) {
      val xDirection: Int = if (key == Input.Keys.A) -1 else 1
      player1Selection.cell(0) = Math.abs(player1Selection.cell(0) + xDirection) % 2
    }

    if (key == Input.Keys.UP || key == Input.Keys.DOWN) {
      val yDirection: Int = if (key == Input.Keys.UP) -1 else 1
      player2Selection.cell(1) = Math.abs(player2Selection.cell(1) + yDirection) % 2
    }
    if (key == Input.Keys.RIGHT || key == Input.Keys.LEFT) {
      val xDirection: Int = if (key == Input.Keys.LEFT) -1 else 1
      player2Selection.cell(0) = Math.abs(player2Selection.cell(0) + xDirection) % 2
    }
  }

  private def handlePlayersCharacterSelection(key: Int): Unit = {
    if (key == Input.Keys.T) {
      selectCharacterFor(player1Selection)
      player1Selection.character.get.position.set(CHARACTER_DISPLAY_X_OFFSET, CHARACTER_DISPLAY_Y_OFFSET)
    }

    if (key == Input.Keys.P) {
      selectCharacterFor(player2Selection)
      player2Selection.character.get.position.set(1920 - CHARACTER_DISPLAY_X_OFFSET - player2Selection.character.get.getCurrentSpriteFrame.getRegionWidth, CHARACTER_DISPLAY_Y_OFFSET)
    }
  }

  private def selectCharacterFor(playerSelection: PlayerSelection): Unit = {
    val col = playerSelection.cell(0)
    val row = playerSelection.cell(1)
    playerSelection.character = Some(Player.createCharacter(playerSelection.id, characterGrid(row)(col).name))
    characterGrid(row)(col).selectionSound.play()
  }

  override def handleControllerButtonDown(game: Game, controller: Controller, i: Int): Unit = {
    if (game.controllersMap(controller) == game.P1Key) {
      if (i == Xbox.X) {
        handlePlayersCharacterSelection(Input.Keys.T) // P1 selection key
      }
    } else if (game.controllersMap(controller) == game.P2Key) {
      if (i == Xbox.X) {
        handlePlayersCharacterSelection(Input.Keys.P)
      }
    }
  }

  override def handleControllerButtonUp(game: Game, controller: Controller, i: Int): Unit = {}

  override def handleControllerPovMoved(game: Game, controller: Controller, povDirection: PovDirection): Unit = {
    if (game.controllersMap(controller) == game.P1Key) {
      var navigationKey = 0
      povDirection match {
        case PovDirection.north => navigationKey = Input.Keys.W
        case PovDirection.south => navigationKey = Input.Keys.S
        case PovDirection.west => navigationKey = Input.Keys.A
        case PovDirection.east => navigationKey = Input.Keys.D
        case _ => return
      }
      handlePlayersGridNavigation(navigationKey)
    } else if (game.controllersMap(controller) == game.P2Key) {
      var navigationKey = 0
      povDirection match {
        case PovDirection.north => navigationKey = Input.Keys.UP
        case PovDirection.south => navigationKey = Input.Keys.DOWN
        case PovDirection.west => navigationKey = Input.Keys.LEFT
        case PovDirection.east => navigationKey = Input.Keys.RIGHT
        case _ => return
      }
      handlePlayersGridNavigation(navigationKey)
    }
  }
}
