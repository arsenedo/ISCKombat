# ISC Kombat
A fighting game inspired from the existing game, mortal kombat.

## About the game
### Overview
ISC Kombat is a 2 player fighting game, where both players fight to their death. Only one can come out alive.
### Game Loop
#### Main menu
In order to start a match, each player needs to choose a character. 
Basic attacks are similar for all characters, though each has their own special attacks.
#### Match
The match is decided by the best of 3. At the end of each round the player's health is completely refilled
#### End
The winning player has a few seconds to perform a fatality on the other one. 
After this sequence, the players are sent back to the main menu.

## Technical details
## Scenes
The game will contain 3 scenes :
1. Character choice
    * In order to start a match, both players need to pick a character
2. Match
   1. Ready up phase (both players are idle and controls are off)
   2. Action phase (players fight freely)
   3. Round end (best of 3)
3. End (Fatality)
   1. If player succeeds in the sequence, fatality executes
   2. Else match ends
## Class Diagram
````mermaid
classDiagram
    class PortableApplication
    class Game {
        +override onInit() Unit
        +override onGraphicsRender() Unit
        +override onGameLogicUpdate() Unit
        +override onKeyUp() Unit
        +override onKeyDown() Unit
    }

    class Character {
        -currentSpriteIndex: Array[Int]
        - position: Vector2
        +drawSprite(g: GdxGraphics) Unit
        +handleInput(action: Action) Unit
        #getSpriteSheet() SpriteSheet
    }

    class State {
        <<Interface>>
        + enter() Unit
        + update() Unit
        + exit() Unit
        + handleInput() Unit
    }

    class ConcreteState

    PortableApplication --|> Game

    State ..|> ConcreteState

    Game "1"-->"2" Character

    Character --> ConcreteState
````