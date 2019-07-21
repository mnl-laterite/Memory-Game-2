package MemoryGame;

import MemoryGame.game.GameLogic;
import MemoryGame.game.Difficulty;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Describes the end game screen.
 *
 * @author mnl-laterite
 */
class EndGameGUI {

  /**
   * Instance of the main app class, needed to change game screens.
   */
  private Main main;

  /**
   * Instance of the game board that records the current state of the game.
   */
  private GameLogic gameLogic;

  /**
   * Parent node for the end game screen layout.
   */
  private VBox endGameLayout;

  /**
   * Container node for the difficulty setting buttons.
   */
  private HBox difficultySettingPanel;

  /**
   * Play again button for the game.
   */
  private Button playAgainButton;

  /**
   * Creates the end game screen of the game.
   *
   * @param main      instance of the main app class.
   * @param gameLogic instance of the underlying game board.
   */
  EndGameGUI (Main main, GameLogic gameLogic) {

    this.main = main;
    this.gameLogic = gameLogic;
    endGameLayout = new VBox();
    endGameLayout.setSpacing(10);

    createDifficultySettingsPanel();

    playAgainButton = new Button("Play again?");
    playAgainButton.setOnAction(e -> showDifficultySettingsPanel());

    Button quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    endGameLayout.setAlignment(Pos.CENTER);
    endGameLayout.getChildren().addAll(playAgainButton, difficultySettingPanel, quitButton);
    endGameLayout.setId("endgamelayout");
  }

  /**
   * Creates the difficulty settings panel.
   */
  private void createDifficultySettingsPanel () {

    difficultySettingPanel = new HBox();
    difficultySettingPanel.setSpacing(10);
    difficultySettingPanel.setVisible(false);
    difficultySettingPanel.setAlignment(Pos.CENTER);

    Button easyButton = new Button("Easy (3x3)");
    difficultySettingPanel.getChildren().add(easyButton);
    easyButton.setOnAction(e -> setDifficultyToEasy());

    Button defaultButton = new Button("Normal (4x4)");
    difficultySettingPanel.getChildren().add(defaultButton);
    defaultButton.setOnAction(e -> setDifficultyToDefault());

    Button hardButton = new Button("Hard (5x5)");
    difficultySettingPanel.getChildren().add(hardButton);
    hardButton.setOnAction(e -> setDifficultyToHard());

  }

  /**
   * Shows the difficulty settings for a new game and hides the play again button.
   */
  private void showDifficultySettingsPanel () {
    difficultySettingPanel.setVisible(true);
    playAgainButton.setVisible(false);
  }

  /**
   * Exits the application.
   */
  private void quitButtonPressed () {
    Platform.exit();
  }

  /**
   * Restarts the game by changing the current screen to the game play screen.
   */
  private void restartGame () {
    playAgainButton.setVisible(true);
    difficultySettingPanel.setVisible(false);
    main.switchToGameLayout();
  }

  /**
   * Sets the game difficulty to easy and restarts the game.
   */
  private void setDifficultyToEasy () {
    gameLogic.resetGame(Difficulty.EASY);
    restartGame();
  }

  /**
   * Sets the game difficulty to default and restarts the game.
   */
  private void setDifficultyToDefault () {
    gameLogic.resetGame(Difficulty.DEFAULT);
    restartGame();
  }

  /**
   * Sets the game difficulty to hard and restarts the game.
   */
  private void setDifficultyToHard () {
    gameLogic.resetGame(Difficulty.HARD);
    restartGame();
  }

  /**
   * @return the parent node for the end screen layout.
   */
  VBox getEndGameLayout () {
    return endGameLayout;
  }
}
