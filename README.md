# TCSS305-Tetris

Autumn 2023 \
This is a tetris game developed for TCSS 305 by group 8.

## Team Members:
- Aly Badr ([badraly@uw.edu](mailto:badraly@uw.edu))
- Lucas Jeong ([ljeong2@uw.edu](mailto:ljeong2@uw.edu))
- Dmitry Khotinskiy ([dkmsh@uw.edu](mailto:dkmsh@uw.edu))
- Brandon Phan ([bdphan@uw.edu](mailto:bdphan@uw.edu))



## Included:
- Checkstyle rule structure
- IntelliJ Clean-code rules
- Package structure
- Driver class with LOGGER object and examples
- Unit test folder
- .gitignore and README.md

<br/>

---

## Sprint 1

### Contributions

- Aly Badr
  - Worked on creating the shapes.
  - Worked on creating the panels and menu for the game window.
- Lucas Jeong
  - Created the methods to allow painting within panels.
  - Created some tetrominos in the game panels.
- Dmitry Khotinskiy
  - Set up initial panels in the main `Tetris` class.
  - Separated panels into separate classes.
  - Cleaned up the warnings before commiting.
- Bradon Phan
  - Double checked the requirements and trying to fix or note any errors.
  - Creating tetrominoes in the game panel.

### Comments

- Used `protected` level of encapsulation to "supress" the warning of
  `'public' method 'paintComponent()' is not exposed via an interface`.

- Hard coded the x- and y-coordinates for the tetromino locations so there are
  warnings `Magic number '_'` and `Actual value of parameter '___' is always '_'`.

<br/>

---

## Sprint 2

### Contributions

- Aly Badr
  - Add property change listener, updater variables, and subscription methods.
  - Implemented singleton pattern and updated applicable classes.
  - Created a next-piece panel with updated pieces.
- Lucas Jeong
  - Revamped the details panel to have functioning lines cleared, score updating, and levels.
  - Fixed an issue where every time new game is pressed, the timer would decrease in delays or ticks.
  - Tested for errors by game testing and made minor changes to various methods.
- Dmitry Khotinskiy
  - Created a game panel with pieces that change and drop.
  - Assisted with creating the key listener.
  - Created an interface that will hold our property change variables.
- Bradon Phan
  - Added Keylisteners for moving Tetrimino along the board.
  - Edited NextPiecePanel to add details to the GUI

[Sprint 2 Google Doc](https://docs.google.com/document/d/1V98TezExMXo0aROVrjR-WPJYtKleT6Mfxb5fInbRbDo/edit)
### Comments

- N/A

---

## Sprint 3

### Contributions

- Aly Badr
  - Added background music, game over, and level-up sound effects.
  - Worked with Lucas on refining the compatibility between sound effects and the scoring panel.
  - Worked with group members to remove warnings and refine code.
- Lucas Jeong
  - Reworked the logic with Aly to fix a problem where the level-up sound played at the start of every game.
  - Created the About scoring panel.
  - Worked with group members to remove warnings and refine code.
- Dmitry Khotinskiy
  - Redesigned the menu bar and added features to control theme color.
  - Added a panel that takes a user's name to store if they get a high score.
  - Worked with group members to remove warnings and refine code.
- Bradon Phan
  - Refined the details panel to look presentable for users.
  - Worked with group members to remove warnings and refine code.

[Sprint 3 Google Doc](https://docs.google.com/document/d/1mwSohJFGUd8fV34zyFNoZvDA-89csSdWmF7WQ5R7XW0/edit?usp=sharing)
### Comments
- Mandatory special feature: Have the user insert a name before starting the game to be stored.
- Extra credits:
  - Background music, level up music, row cleared music, and game over music.
  - Appearance: dark mode and light mode for all windows.
  - Leaderboard (⌘ + L) that picks up user's name.
- Additional info:
  - Number of lines needed to reach next level: `DetailsPanel.java` line 186.
  - Lines cleared algorithm: `DetailsPanel.java` line 187 with help in `AboutScoreFrame.java`.

### Sources:
- [AUDIO](https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/Clip.html)
- [Tetris Music](https://www.101soundboards.com/boards/10378-tetris-sounds)
- [Pokemon Level Up Sound](https://www.myinstants.com/en/search/?name=pokemon)
- [Game Over Sound](https://opengameart.org/content/512-sound-effects-8-bit-style)
