package MemoryGame;

import MemoryGame.game.GameLogic;
import MemoryGame.game.Difficulty;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EndGameGUI {

  private Main main;
  private GameLogic gameLogic;

  private VBox endGameLayout;
  private HBox endGameSettingsPanel;

  private Button easyButton;
  private Button defaultButton;
  private Button hardButton;

  private Button playAgainButton;
  private Button quitButton;

  public EndGameGUI (Main main, GameLogic gameLogic) {

    this.main = main;
    this.gameLogic = gameLogic;
    endGameLayout = new VBox();
    endGameLayout.setSpacing(10);

    createEndGameSettingsPanel();

    playAgainButton = new Button("Play again?");
    playAgainButton.setOnAction(e -> playAgainButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    endGameLayout.setAlignment(Pos.CENTER);
    endGameLayout.getChildren().addAll(playAgainButton,endGameSettingsPanel,quitButton);
    endGameLayout.setId("endgamelayout");
  }

  private void createEndGameSettingsPanel () {

    endGameSettingsPanel = new HBox();
    endGameSettingsPanel.setSpacing(10);
    endGameSettingsPanel.setVisible(false);
    endGameSettingsPanel.setAlignment(Pos.CENTER);

    easyButton = new Button("Easy (3x3)");
    endGameSettingsPanel.getChildren().add(easyButton);
    easyButton.setOnAction(e -> easyButtonPressed());

    defaultButton = new Button("Normal (4x4)");
    endGameSettingsPanel.getChildren().add(defaultButton);
    defaultButton.setOnAction(e -> defaultButtonPressed());

    hardButton = new Button("Hard (5x5)");
    endGameSettingsPanel.getChildren().add(hardButton);
    hardButton.setOnAction(e -> hardButtonPressed());

  }

  private void playAgainButtonPressed () {
    endGameSettingsPanel.setVisible(true);
    playAgainButton.setVisible(false);
  }

  private void quitButtonPressed () {
    Platform.exit();
  }

  private void difficultyButtonPressed() {
    playAgainButton.setVisible(true);
    endGameSettingsPanel.setVisible(false);
    main.setScene(main.getGameScene());
  }

  private void easyButtonPressed () {
    gameLogic.resetGame(Difficulty.EASY);
    difficultyButtonPressed();
  }

  private void defaultButtonPressed () {
    gameLogic.resetGame(Difficulty.DEFAULT);
    difficultyButtonPressed();
  }

  private void hardButtonPressed () {
    gameLogic.resetGame(Difficulty.HARD);
    difficultyButtonPressed();
  }

  public VBox getEndGameLayout () {
    return endGameLayout;
  }
}
