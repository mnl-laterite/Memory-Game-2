package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

/**
 * Describes the main menu screen.
 * @author mnl-laterite
 */
class MainMenuGUI {

  /**
   * Instance of the main class, needed to change screens.
   */
  private Main main;

  /**
   * The parent node that contains all main menu GUI elements.
   */
  private VBox menuLayout;

  /**
   * Container node for difficulty setting buttons.
   */
  private HBox difficultySettingsPanel;

  /**
   * Game start button.
   */
  private Button startButton;

  /**
   * Instance of the underlying game board on which the game is played.
   */
  private GameLogic gameLogic;

  /**
   * Creates the main screen of the game.
   * @param main instance of the main class, needed to change game screens.
   * @param gameLogic instance of the game board, needed to draw the pieces according to game state.
   */
  MainMenuGUI(Main main, GameLogic gameLogic) {

    this.main = main;
    this.gameLogic = gameLogic;

    menuLayout = new VBox();
    menuLayout.setSpacing(10);

    createDifficultySettingsPanel();

    startButton = new Button("Start!");
    startButton.setOnAction(e -> showDifficultySettingsPanel());

    Button quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    menuLayout.setAlignment(Pos.CENTER);
    menuLayout.getChildren().addAll(startButton, difficultySettingsPanel, quitButton);
    menuLayout.setId("menulayout");

  }

  /**
   * Creates the difficulty settings panel.
   */
  private void createDifficultySettingsPanel() {

    difficultySettingsPanel = new HBox();
    difficultySettingsPanel.setSpacing(10);
    difficultySettingsPanel.setVisible(false);
    difficultySettingsPanel.setAlignment(Pos.CENTER);

    Button easyButton = new Button("Easy (3x3)");
    difficultySettingsPanel.getChildren().add(easyButton);
    easyButton.setOnAction(e -> setDifficultyToEasy());

    Button defaultButton = new Button("Normal (4x4)");
    difficultySettingsPanel.getChildren().add(defaultButton);
    defaultButton.setOnAction(e -> setDifficultyToDefault());

    Button hardButton = new Button("Hard (5x5)");
    difficultySettingsPanel.getChildren().add(hardButton);
    hardButton.setOnAction(e -> setDifficultyToHard());
  }

  /**
   * Exits the application.
   */
  private void quitButtonPressed () {
    Platform.exit();
  }

  /**
   * Makes the difficulty settings panel visible to the user and hides the start button.
   */
  private void showDifficultySettingsPanel () {
    difficultySettingsPanel.setVisible(true);
    startButton.setVisible(false);
  }

  /**
   * Sets the difficulty of the game to easy and starts the game.
   */
  private void setDifficultyToEasy () {
    gameLogic.resetGame(Difficulty.EASY);
    startGame();
  }

  /**
   * Sets the difficulty of the game to default/normal and starts the game.
   */
  private void setDifficultyToDefault () {
    gameLogic.resetGame(Difficulty.DEFAULT);
    startGame();
  }

  /**
   * Sets the difficulty of the game to hard and starts the game.
   */
  private void setDifficultyToHard () {
    gameLogic.resetGame(Difficulty.HARD);
    startGame();
  }

  /**
   * Starts the game by changing the main menu screen to the game screen.
   */
  private void startGame() {
    main.switchToGameLayout();
  }

  /**
   * @return the parents node containing the main menu layout.
   */
  VBox getMenuLayout() {
    return menuLayout;
  }

}
